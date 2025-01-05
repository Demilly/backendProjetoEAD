package br.com.ead.service;


import br.com.ead.controller.request.UsuarioRequest;
import br.com.ead.controller.response.UsuarioResponse;

public interface UsuarioService {

    UsuarioResponse salvarUsuario(UsuarioRequest usuarioRequest);

    UsuarioResponse buscarUsuarioPorId(Long id);

    UsuarioResponse buscarUsuarioPorEmail(String email);

    void deletarUsuario(Long id);

}
