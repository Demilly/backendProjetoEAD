package br.com.ead.service;

import br.com.ead.controller.response.auth.LoginResponse;
import br.com.ead.model.entity.usuario.Usuario;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {

    LoginResponse authenticate(String email, String senha);

    UserDetails loadUserByUsername(String email);

    void register(Usuario usuario);
}
