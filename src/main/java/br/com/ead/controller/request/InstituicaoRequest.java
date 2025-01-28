package br.com.ead.controller.request;

import lombok.Data;

@Data
public class InstituicaoRequest {

    private String nomeInstituicao;
    private String cpfOuCnpj;
    private Integer quantidadeLicencasProfessor;
    private Integer quantidadeLicencasAluno;
}
