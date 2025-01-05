package br.com.ead.controller.response.ensino.aula;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProgressaoAulaReponse {

    private Boolean statusConcluido;
    private LocalDateTime dataUltimoAcesso;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataConclusao;
}
