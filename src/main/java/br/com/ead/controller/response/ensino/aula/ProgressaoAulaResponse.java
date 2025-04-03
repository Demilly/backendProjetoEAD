package br.com.ead.controller.response.ensino.aula;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProgressaoAulaResponse {

    private String uuid;
    private String tituloAula;
    private String descricaoAula;
    private Boolean statusConcluido;
    private LocalDateTime dataUltimoAcesso;
    private LocalDateTime dataConclusao;
    private String tituloModulo;
    private String nomeCurso;
}
