package br.com.ead.controller.response.ensino.aula;

import lombok.Data;

import java.util.List;

@Data
public class AulaResponse {

    private String titulo;
    private String descricao;
    private Integer duracaoMinutos;
    private Integer ordemAula;
    private List<VideoAulaResponse> videos;
    private List<ComentarioResponse> comentarios;
    private List<ProgressaoAulaResponse> progressaoAulas;
}
