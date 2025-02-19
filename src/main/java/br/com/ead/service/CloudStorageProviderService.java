package br.com.ead.service;

import br.com.ead.controller.response.UploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CloudStorageProviderService {

    UploadResponse uploadArquivo(MultipartFile arquivo, String tipo);

    void deletarArquivo(String nomeArquivo, String tipo);
}
