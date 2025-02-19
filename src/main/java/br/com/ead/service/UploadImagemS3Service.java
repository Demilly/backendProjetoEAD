package br.com.ead.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface UploadImagemS3Service {

    String uploadFile(MultipartFile file);

    String uploadFile(InputStream inputStream, String nomeArquivo, String contentType);
}
