package br.com.ead.service;

import br.com.ead.controller.response.UploadResponse;
import br.com.ead.service.exception.FileUploadException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CloudStorageProviderServiceImpl implements CloudStorageProviderService {

    private final S3Client s3Client;
    private final ImageService imageService;
    private final ArmazenamentoProperties armazenamentoProperties;

    @Override
    public UploadResponse uploadArquivo(MultipartFile arquivo, String tipo) {
        try {
            String caminhoArquivo = sanitizarCaminhoArquivo(arquivo, tipo);

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(armazenamentoProperties.getS3().getBucket())
                    .key(caminhoArquivo)
                    .contentType(arquivo.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(arquivo.getInputStream(), arquivo.getSize()));

            String fileUrl = gerarUrlArquivo(caminhoArquivo);

            return new UploadResponse(fileUrl, "Arquivo carregado com sucesso.");

        } catch (IOException e) {
            throw new FileUploadException("Falha ao fazer o upload do arquivo", e);
        }
    }

    public UploadResponse uploadArquivo(InputStream inputStream, String caminhoArquivo, String contentType, long tamanhoArquivo) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(armazenamentoProperties.getS3().getBucket())
                .key(caminhoArquivo)
                .contentType(contentType)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, tamanhoArquivo));

        String fileUrl = gerarUrlArquivo(caminhoArquivo);

        return new UploadResponse(fileUrl, "Arquivo carregado com sucesso.");

    }

    @Override
    public void deletarArquivo(String nomeArquivo, String tipo) {
        String nomeArquivoUrl = extrairNomeArquivoDaUrl(nomeArquivo);

        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(armazenamentoProperties.getS3().getBucket())
                .key(nomeArquivoUrl)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }

    private String extrairNomeArquivoDaUrl(String imagemUrl) {
        String bucketUrlPrefix = "https://" + armazenamentoProperties.getS3().getBucket() + ".s3." + armazenamentoProperties.getS3().getRegion() + ".amazonaws.com/";
        return imagemUrl.replace(bucketUrlPrefix, "");
    }

    private String gerarUrlArquivo(String caminhoArquivo) {
        return "https://" + armazenamentoProperties.getS3().getBucket() + ".s3." + armazenamentoProperties.getS3().getRegion() + ".amazonaws.com/" + caminhoArquivo;
    }

    private static String sanitizarCaminhoArquivo(MultipartFile arquivo, String tipo) {
        String nomeArquivo = arquivo.getOriginalFilename();
        if (nomeArquivo == null) {
            nomeArquivo = "arquivo_sem_nome";
        }
        return tipo + "/" + UUID.randomUUID() + "_" + nomeArquivo.replaceAll("\\s+", "_");
    }


    //TODO - DEIXEI ESSE METODO CASO SEJA NECESSARIO FUTURAMENTE PROCESSAR AS IMAGENS JÁ ESTÁ PRONTINHO
    public UploadResponse processarImagem(MultipartFile imagem) {
        if (imagem == null || imagem.isEmpty()) {
            throw new FileUploadException("Imagem inválida para processamento.");
        }
        try {
            String nomeArquivo = "image/" + UUID.randomUUID() + ".jpg";

            BufferedImage jpgImage = imageService.getJpgImageFromFile(imagem);

            jpgImage = imageService.cropSquare(jpgImage);
            int tamanho = 600;
            jpgImage = imageService.resize(jpgImage, tamanho);

            InputStream inputStream = imageService.getInputStream(jpgImage, "jpg");

            return uploadArquivo(inputStream, nomeArquivo, "image/jpeg", inputStream.available());
        } catch (IOException e) {
            throw new FileUploadException("Falha ao processar e fazer upload da imagem", e);
        }
    }
}
