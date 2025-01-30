package br.com.ead.controller.request.ensino.aula;

import br.com.ead.controller.request.QuestaoRequest;
import br.com.ead.controller.request.VideoAulaRequest;
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
