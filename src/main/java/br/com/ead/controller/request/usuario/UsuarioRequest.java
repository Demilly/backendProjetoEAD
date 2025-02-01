package br.com.ead.controller.request.usuario;

import br.com.ead.model.enums.TipoUsuarioEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UsuarioRequest {

    @NotBlank(message = "email obrigatório")
    private String email;
    @NotBlank(message = "senha obrigatória")
    private String senha;
    @NotNull(message = "Tipo de usuário obrigatório")
    @Schema(description = "Tipo de usuário", example = "ADMINISTRADOR", allowableValues = "ADMINISTRADOR, PROFESSOR, ALUNO")
    private TipoUsuarioEnum tipoUsuario;
    private String instituicao;
    private List<TelefoneRequest> telefones;
    private String nome;
    private String sobrenome;
}
