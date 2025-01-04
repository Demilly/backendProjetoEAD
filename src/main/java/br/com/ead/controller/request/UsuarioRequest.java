package br.com.ead.controller.request;

import br.com.ead.model.enums.TipoUsuarioEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsuarioRequest {

    @NotBlank(message = "email obrigatório")
    private String email;
    @NotBlank(message = "senha obrigatória")
    private String senha;
    @NotNull(message = "Tipo de usuário obrigatório")
    @Schema(description = "Tipo de usuário", example = "ADMINISTRADOR", allowableValues = "ADMINISTRADOR, PROFESSOR, ALUNO")
    private TipoUsuarioEnum tipoUsuario;
    private InstituicaoRequest instituicao;
//    private List<TelefoneRequest> telefones;
}
