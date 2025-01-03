package br.com.ead.service;


import br.com.ead.controller.response.UsuarioResponse;
import br.com.ead.model.entity.usuario.Usuario;

public interface UsuarioService {

    UsuarioResponse salvarUsuario(Usuario usuario);
}
