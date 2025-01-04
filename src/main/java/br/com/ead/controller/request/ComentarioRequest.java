package br.com.ead.controller.request;

import lombok.Data;

@Data
public class ComentarioRequest {

    private String texto;
    private QuestaoRequest questao;
}
