package br.com.ead.service;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.InputStream;

public interface ImageService {

    BufferedImage getJpgImageFromFile(MultipartFile multipartFile);

    BufferedImage pngToJpg(BufferedImage imagem);

    InputStream getInputStream(BufferedImage bufferedImage, String extension);

    BufferedImage cropSquare(BufferedImage sourceImg);

    BufferedImage resize(BufferedImage sourceImg, int size);
}
