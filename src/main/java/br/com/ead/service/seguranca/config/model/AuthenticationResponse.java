package br.com.ead.service.seguranca.config.model;

import br.com.ead.controller.response.usuario.UsuarioResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponse {
    private UsuarioResponse usuario;
    private String token;
}

