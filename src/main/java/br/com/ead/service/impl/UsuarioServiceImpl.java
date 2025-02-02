package br.com.ead.service.impl;

import br.com.ead.controller.request.usuario.UsuarioRequest;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {

    public static final String LICENCA_PROFESSOR_ATINGIDA = "Limite de licenças para professores atingido na instituição.";
    public static final String LICENCA_ALUNO_ATINGIDA = "Limite de licenças para alunos atingido na instituição.";
    public static final String PERFIL_INVALIDO = "Perfil inválido: ";
    public static final String INSTITUICAO_PADRAO_NAO_ENCONTRADA = "Instituição padrão não encontrada.";
    public static final String INSTITUICAO_INFORMADA_NAO_ENCONTRADA = "Instituição Informada não encontrada.";
    public static final String DEFAULT_INSTITUICAO_CNPJ = "string";

    private final UsuarioRepository usuarioRepository;
    private final InstituicaoRepository instituicaoRepository;
    private final TelefoneMapper telefoneMapper;
    private final TelefoneRepository telefoneRepository;
    private final UsuarioMapper usuarioMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UsuarioResponse salvarUsuario(UsuarioRequest usuarioRequest) {
        var usuarioEntity = usuarioMapper.toUsuario(usuarioRequest);
        determinaInstituicao(usuarioRequest, usuarioEntity);

        if (isProfessorOuAluno(usuarioRequest)) {
            verificarLicencasCadastradas(usuarioRequest.getTipoUsuario(), usuarioEntity.getInstituicao());
        }

        usuarioRequest.getTelefones()
                .stream()
                .map(telefoneMapper::toTelefone)
                .forEach(usuarioEntity::addTelefone);

        usuarioEntity.setSenha(passwordEncoder.encode(usuarioRequest.getSenha()));
        Usuario usuarioSalvo = usuarioRepository.save(usuarioEntity);
        telefoneRepository.saveAll(usuarioSalvo.getTelefones());
        return usuarioMapper.toUsuarioResponse(usuarioSalvo);
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
        Page<Usuario> usuarios = usuarioRepository.findByTipoUsuario(tipoUsuarioEnum, pageable);
        return usuarios.map(usuarioMapper::toUsuarioResponse);
    }

    private void determinaInstituicao(UsuarioRequest usuarioRequest, Usuario usuario) {
        Instituicao instituicao = usuarioRequest.getInstituicao() == null || usuarioRequest.getInstituicao().isEmpty()
                ? buscarInstituicaoPadrao()
                : buscarInstituicao(usuarioRequest);
        usuario.setInstituicao(instituicao);
    }

    private Instituicao buscarInstituicaoPadrao() {
        return instituicaoRepository.findByCpfOuCnpj(DEFAULT_INSTITUICAO_CNPJ)
                .orElseThrow(() -> new IllegalStateException(INSTITUICAO_PADRAO_NAO_ENCONTRADA));
    }

    private Instituicao buscarInstituicao(UsuarioRequest usuarioRequest) {
        return instituicaoRepository.findByCpfOuCnpj(usuarioRequest.getInstituicao())
                .orElseThrow(() -> new IllegalStateException(INSTITUICAO_INFORMADA_NAO_ENCONTRADA));
    }

    private boolean isProfessorOuAluno(UsuarioRequest usuarioRequest) {
        return TipoUsuarioEnum.PROFESSOR.equals(usuarioRequest.getTipoUsuario()) ||
                TipoUsuarioEnum.ALUNO.equals(usuarioRequest.getTipoUsuario());
    }

    private void verificarLicencasCadastradas(TipoUsuarioEnum perfil, Instituicao instituicao) {
        switch (perfil) {
            case PROFESSOR:
                validarLicencas(instituicao.getQuantidadeLicencasProfessor(), LICENCA_PROFESSOR_ATINGIDA);
                instituicao.setQuantidadeLicencasProfessor(instituicao.getQuantidadeLicencasProfessor() - 1);
                break;
            case ALUNO:
                validarLicencas(instituicao.getQuantidadeLicencasAluno(), LICENCA_ALUNO_ATINGIDA);
                instituicao.setQuantidadeLicencasAluno(instituicao.getQuantidadeLicencasAluno() - 1);
                break;
            default:
                throw new IllegalArgumentException(PERFIL_INVALIDO + perfil);
        }
        instituicaoRepository.save(instituicao);
    }

    private void validarLicencas(Integer quantidadeLicencas, String mensagemErro) {
        if (quantidadeLicencas <= 0) {
            throw new BusinessException(mensagemErro);
        }
    }
}
