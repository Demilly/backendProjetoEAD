package br.com.ead.controller.request;

import lombok.Data;

import java.util.List;

@Data
public class AulaRequest {

    private String titulo;
    private String descricao;
    private Integer duracaoMinutos;
    private Integer ordemAula;
    private List<VideoAulaRequest> videos;
    private List<QuestaoRequest> questoes;
}
