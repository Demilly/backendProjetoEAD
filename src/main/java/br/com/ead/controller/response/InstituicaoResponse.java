package br.com.ead.controller.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InstituicaoResponse {

    private String nomeInstituicao;
    private String cpfOuCnpj;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
}
