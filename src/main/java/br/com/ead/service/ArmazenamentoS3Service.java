package br.com.ead.service;

import br.com.ead.controller.response.UploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ArmazenamentoS3Service {

    UploadResponse uploadDocumento(MultipartFile arquivo, String tipo);

    UploadResponse uploadImagem(MultipartFile arquivo, String tipo);

    void deletarArquivo(String nomeArquivo, String tipo);
}
