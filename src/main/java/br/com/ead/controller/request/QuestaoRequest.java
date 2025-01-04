package br.com.ead.controller.request;

import lombok.Data;

@Data
public class QuestaoRequest {

    private String tituloQuestao;
    private String descricao;
    private String respostaCorreta;
    private Integer pontuacao;
}
