package br.com.ead.controller.request;

import lombok.Data;

@Data
public class RespostaRequest {
    private String descricao;
    private boolean correta;
    private String opcao;
}
