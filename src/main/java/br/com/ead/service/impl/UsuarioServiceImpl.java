package br.com.ead.service.impl;


import br.com.ead.controller.request.usuario.UsuarioRequest;
import br.com.ead.controller.request.usuario.UsuarioUpdateRequest;
import br.com.ead.controller.response.usuario.UsuarioResponse;
import br.com.ead.model.entity.instituicao.Instituicao;
import br.com.ead.model.entity.usuario.Usuario;
import br.com.ead.model.enums.TipoUsuarioEnum;
import br.com.ead.model.mapper.TelefoneMapper;
import br.com.ead.model.mapper.UsuarioMapper;
import br.com.ead.repository.InstituicaoRepository;
import br.com.ead.repository.TelefoneRepository;
import br.com.ead.repository.UsuarioRepository;
import br.com.ead.service.UsuarioService;
import br.com.ead.service.exception.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final InstituicaoRepository instituicaoRepository;
    private final TelefoneMapper telefoneMapper;
    private final TelefoneRepository telefoneRepository;
    private final UsuarioMapper usuarioMapper;

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
    public UsuarioResponse atualizarInstituicao(String cpfCnpj, UsuarioUpdateRequest usuarioUpdateRequest) {
        var usuarioExistente = usuarioRepository.findByCpfOuCnpj(cpfCnpj)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        usuarioExistente.setEmail(usuarioUpdateRequest.getEmail());
        usuarioExistente.setNome(usuarioUpdateRequest.getNome());
        usuarioExistente.setSobrenome(usuarioUpdateRequest.getSobrenome());
        usuarioExistente.setCpfOuCnpj(usuarioUpdateRequest.getCpfOuCnpj());
        usuarioExistente.setStatusUsuario(usuarioUpdateRequest.getStatusUsuario());
        usuarioExistente.setInstituicao(usuarioExistente.getInstituicao());
        usuarioExistente.setSenha(usuarioExistente.getSenha());

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
