package br.com.ead.controller.response;

import lombok.Data;

@Data
public class RespostaResponse {

    private String uuid;
    private String descricao;
    private String opcao;
    private boolean correta;
}
