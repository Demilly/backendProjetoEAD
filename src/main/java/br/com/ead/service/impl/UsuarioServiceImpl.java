package br.com.ead.service.impl;


import br.com.ead.controller.response.UsuarioResponse;
import br.com.ead.model.entity.instituicao.Instituicao;
import br.com.ead.model.entity.usuario.Telefone;
import br.com.ead.model.entity.usuario.Usuario;
import br.com.ead.repository.InstituicaoRepository;
import br.com.ead.repository.TelefoneRepository;
import br.com.ead.repository.UsuarioRepository;
import br.com.ead.service.UsuarioService;
import br.com.ead.service.mapper.TelefoneMapper;
import br.com.ead.service.mapper.UsuarioMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public UsuarioResponse salvarUsuario(Usuario usuario) {
        Instituicao instituicao = determinaInstituicao(usuario);
        usuario.setInstituicao(instituicao);

        List<Telefone> telefones = usuario.getTelefones();
        telefones.forEach(usuario::addTelefone);


        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        telefoneRepository.saveAll(usuarioSalvo.getTelefones());
        return usuarioMapper.toUsuarioResponse(usuarioSalvo);
    }

    private Instituicao determinaInstituicao(Usuario usuario) {
        if (usuario.getInstituicao() == null) {
            usuario.setInstituicao(buscarInstituicaoPadrao());
        } else {
            usuario.setInstituicao(buscarInstituicao(usuario));
        }
        return usuario.getInstituicao();
    }

    private Instituicao buscarInstituicaoPadrao() {
        return instituicaoRepository
                .findByCpfOuCnpj("string")
                .orElseThrow(() -> new IllegalStateException("Instituição padrão não encontrada."));
    }

    private Instituicao buscarInstituicao(Usuario usuario) {
        var usuarioInstituicao = usuario.getInstituicao().getCpfOuCnpj();
        return instituicaoRepository
                .findByCpfOuCnpj(usuarioInstituicao)
                .orElseThrow(() -> new IllegalStateException("Instituição Informada não encontrada."));
    }

}
