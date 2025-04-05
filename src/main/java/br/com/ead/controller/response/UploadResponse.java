package br.com.ead.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class UploadResponse {

    private String caminhoArquivo;
    private String mensagem;
    private LocalDateTime dataHoraUpload;
}
