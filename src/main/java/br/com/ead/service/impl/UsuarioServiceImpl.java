package br.com.ead.service.impl;


import br.com.ead.controller.request.usuario.UsuarioRequest;
import br.com.ead.controller.request.usuario.UsuarioUpdateRequest;
import br.com.ead.controller.response.usuario.UsuarioResponse;
import br.com.ead.model.entity.ensino.Curso;
import br.com.ead.model.entity.instituicao.Instituicao;
import br.com.ead.model.entity.usuario.Usuario;
import br.com.ead.model.enums.TipoUsuarioEnum;
import br.com.ead.model.mapper.TelefoneMapper;
import br.com.ead.model.mapper.UsuarioMapper;
import br.com.ead.repository.InstituicaoRepository;
import br.com.ead.repository.TelefoneRepository;
import br.com.ead.repository.UsuarioRepository;
import br.com.ead.service.ArmazenamentoS3Service;
import br.com.ead.service.UsuarioService;
import br.com.ead.service.exception.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final InstituicaoRepository instituicaoRepository;
    private final TelefoneMapper telefoneMapper;
    private final TelefoneRepository telefoneRepository;
    private final UsuarioMapper usuarioMapper;
    private ArmazenamentoS3Service armazenamentoS3Service;

    @Override
    @Transactional
    public UsuarioResponse salvarUsuario(UsuarioRequest usuarioRequest) {

        var usuarioEntity = usuarioMapper.toUsuario(usuarioRequest);

        determinaInstituicao(usuarioRequest, usuarioEntity);

        usuarioRequest.getTelefones()
                .stream()
                .map(telefoneMapper::toTelefone)
                .forEach(usuarioEntity::addTelefone);

        Usuario usuarioSalvo = usuarioRepository.save(usuarioEntity);
        telefoneRepository.saveAll(usuarioSalvo.getTelefones());
        return usuarioMapper.toUsuarioResponse(usuarioSalvo);
    }

    @Override
    public UsuarioResponse salvarUsuario(UsuarioRequest usuarioRequest, MultipartFile imagem) {
        var usuarioEntity = usuarioMapper.toUsuario(usuarioRequest);

        determinaInstituicao(usuarioRequest, usuarioEntity);

//        usuarioRequest.getTelefones()
//                .stream()
//                .map(telefoneMapper::toTelefone)
//                .forEach(usuarioEntity::addTelefone);

        if (imagem != null && !imagem.isEmpty()) {
            uploadS3(imagem, usuarioEntity);
        }

        Usuario usuarioSalvo = usuarioRepository.save(usuarioEntity);
        telefoneRepository.saveAll(usuarioSalvo.getTelefones());
        return usuarioMapper.toUsuarioResponse(usuarioSalvo);
    }

    private void uploadS3(MultipartFile imagem, Usuario usuarioEntity) {

        // Deleta a imagem do S3, se tiver uma URL válida
        if (usuarioEntity.getUrlImagem() != null && !usuarioEntity.getUrlImagem().isBlank() && !usuarioEntity.getUrlImagem().isEmpty()) {
            armazenamentoS3Service.deletarArquivo(usuarioEntity.getUrlImagem(), "usuario");
        }

        var responseS3 = armazenamentoS3Service.uploadImagem(imagem, "usuario/"+usuarioEntity.getTipoUsuario().name());

        if(responseS3 != null && !responseS3.getCaminhoArquivo().isEmpty()) {
            usuarioEntity.setUrlImagem(responseS3.getCaminhoArquivo());
        }
    }

    @Override
    public UsuarioResponse atualizarUsuario(String cpfCnpj, UsuarioUpdateRequest usuarioUpdateRequest, MultipartFile imagem) {
        var usuarioExistente = usuarioRepository.findByCpfOuCnpj(cpfCnpj)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        usuarioExistente.setEmail(usuarioUpdateRequest.getEmail());
        usuarioExistente.setNome(usuarioUpdateRequest.getNome());
        usuarioExistente.setSobrenome(usuarioUpdateRequest.getSobrenome());
        usuarioExistente.setCpfOuCnpj(usuarioUpdateRequest.getCpfOuCnpj());
        usuarioExistente.setStatusUsuario(usuarioUpdateRequest.getStatusUsuario());
        usuarioExistente.setInstituicao(usuarioExistente.getInstituicao());
        usuarioExistente.setSenha(usuarioExistente.getSenha());

        if (imagem != null && !imagem.isEmpty()) {
            uploadS3(imagem, usuarioExistente);
        }


        var usuarioAtualizado = usuarioRepository.save(usuarioExistente);
        return usuarioMapper.toUsuarioResponse(usuarioAtualizado);
    }

    @Override
    @Transactional
    public UsuarioResponse buscarUsuarioPorId(Long id) {
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + id));

        return usuarioMapper.toUsuarioResponse(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponse buscarUsuarioPorEmail(String email) {
        var usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com EMAIL: " + email));

        return usuarioMapper.toUsuarioResponse(usuario);
    }

    @Override
    @Transactional
    public void deletarUsuario(String cpfOuCnpj) {
        Usuario usuario = usuarioRepository.findByCpfOuCnpj(cpfOuCnpj)
                .orElseThrow(() -> new BusinessException("Usuário não localizado para o ID informado."));
        usuarioRepository.delete(usuario);
    }

    @Override
    public Page<UsuarioResponse> buscarPorTipoUsuarioPaginado(TipoUsuarioEnum tipoUsuarioEnum, Pageable pageable) {
        Page<Usuario> usuarios = usuarioRepository.
                findByTipoUsuario(tipoUsuarioEnum, pageable);
        return usuarios.map(usuarioMapper::toUsuarioResponse);
    }

    @Override
    public Page<UsuarioResponse> buscarPorTiposUsuarioPaginado(List<TipoUsuarioEnum> tiposUsuarios, Pageable pageable) {
        Page<Usuario> usuarios = usuarioRepository.findByTipoUsuarioIn(tiposUsuarios, pageable);

        // Converte a lista de Usuario para UsuarioResponse
        return usuarios.map(usuarioMapper::toUsuarioResponse);
    }

    private void determinaInstituicao(UsuarioRequest usuarioRequest, Usuario usuario) {
        if (usuarioRequest.getInstituicao() == null || usuarioRequest.getInstituicao().isEmpty()) {
            usuario.setInstituicao(buscarInstituicaoPadrao());
        } else {
            usuario.setInstituicao(buscarInstituicao(usuarioRequest));
        }
    }

    private Instituicao buscarInstituicaoPadrao() {
        return instituicaoRepository
                .findByCpfOuCnpj("string")
                .orElseThrow(() -> new IllegalStateException("Instituição padrão não encontrada."));
    }

    private Instituicao buscarInstituicao(UsuarioRequest usuarioRequest) {
        var usuarioInstituicao = usuarioRequest.getInstituicao();
        return instituicaoRepository
                .findByCpfOuCnpj(usuarioInstituicao)
                .orElseThrow(() -> new IllegalStateException("Instituição Informada não encontrada."));
    }

}
