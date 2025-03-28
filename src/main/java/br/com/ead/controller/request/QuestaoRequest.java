package br.com.ead.controller.request;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class QuestaoRequest {

    private String pergunta;
    private String descricao;
    private Integer pontuacao;
    private String explicacao;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private String uuidModulo;
    private String uuidCurso;
    private Set<RespostaRequest> respostas;
}
