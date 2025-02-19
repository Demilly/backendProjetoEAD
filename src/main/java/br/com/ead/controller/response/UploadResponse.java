package br.com.ead.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UploadResponse {

    private String caminhoArquivo;
    private String mensagem;
}
