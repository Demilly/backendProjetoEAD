package br.com.ead.controller.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VideoAulaRequest {

    private String url;
    private Long tamanhoMb;
    private Long duracao;
    private LocalDateTime dataUpload;
}
