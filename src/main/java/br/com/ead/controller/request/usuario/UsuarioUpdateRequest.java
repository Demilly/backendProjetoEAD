package br.com.ead.controller.request.usuario;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class UsuarioUpdateRequest {

    @NotBlank(message = "email obrigatório")
    private String email;
    private String senha;
    private List<TelefoneRequest> telefones;
    private String nome;
    private String sobrenome;
    private Boolean statusUsuario;
    @NotBlank(message = "cpf ou cnpj obrigatório")
    private String cpfOuCnpj;
}
