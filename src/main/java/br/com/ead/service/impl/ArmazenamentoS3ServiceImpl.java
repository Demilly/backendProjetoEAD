package br.com.ead.service.impl;

import br.com.ead.controller.response.UploadResponse;
import br.com.ead.service.ArmazenamentoS3Service;
import br.com.ead.service.CloudStorageProviderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Service
public class ArmazenamentoS3ServiceImpl implements ArmazenamentoS3Service {

    private final CloudStorageProviderService cloudStorageProviderService;

    @Override
    public UploadResponse uploadDocumento(MultipartFile arquivo, String tipo) {
        return cloudStorageProviderService.uploadArquivo(arquivo, tipo);
    }

    @Override
    public UploadResponse uploadImagem(MultipartFile arquivo, String tipo) {
        return cloudStorageProviderService.uploadArquivo(arquivo, tipo);
    }

    @Override
    public void deletarArquivo(String nomeArquivo, String tipo) {
        cloudStorageProviderService.deletarArquivo(nomeArquivo, tipo);
    }
}
