package br.com.ead.controller.request.instituicao;

import lombok.Data;

import java.util.List;

@Data
public class InstituicaoRequest {

    private String nomeInstituicao;
    private String cpfOuCnpj;
    private Integer quantidadeLicencasProfessor;
    private Integer quantidadeLicencasAluno;
    private List<String> uuidCurso;
    private Boolean ativa;
}
