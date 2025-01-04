package br.com.ead.controller.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProgressaoAulaRequest {

    private Boolean statusConcluido;
    private LocalDateTime dataUltimoAcesso;
    private LocalDateTime dataConclusao;
}
