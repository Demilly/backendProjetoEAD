package br.com.ead.controller.response.instituicao;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InstituicaoResponse {

    private String nomeInstituicao;
    private String cpfOuCnpj;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private Integer quantidadeLicencasProfessor;
    private Integer quantidadeLicencasAluno;
}
