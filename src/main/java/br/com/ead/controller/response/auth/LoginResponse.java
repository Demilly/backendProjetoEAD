package br.com.ead.controller.response.auth;

import br.com.ead.controller.response.usuario.UsuarioResponse;
import br.com.ead.model.enums.TipoUsuarioEnum;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponse {

    private String email;
    private String token;
    private TipoUsuarioEnum tipoUsuario;
    private UsuarioResponse usuario;
}
