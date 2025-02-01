package br.com.ead.service;


import br.com.ead.controller.request.usuario.UsuarioRequest;
import br.com.ead.controller.response.usuario.UsuarioResponse;
import br.com.ead.model.enums.TipoUsuarioEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioService {

    UsuarioResponse salvarUsuario(UsuarioRequest usuarioRequest);

    UsuarioResponse buscarUsuarioPorId(Long id);

    UsuarioResponse buscarUsuarioPorEmail(String email);

    void deletarUsuario(String cpfOuCnpj);

    Page<UsuarioResponse> buscarPorTipoUsuarioPaginado(TipoUsuarioEnum tipoUsuarioEnum, Pageable pageable);
}
