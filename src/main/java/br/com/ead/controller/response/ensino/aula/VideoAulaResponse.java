package br.com.ead.controller.response.ensino.aula;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VideoAulaResponse {

    private String uuid;
    private String url;
    private Long duracao;
    private LocalDateTime dataUpload;
}
