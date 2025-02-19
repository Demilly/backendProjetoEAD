package br.com.ead.controller;

import br.com.ead.controller.response.UploadResponse;
import br.com.ead.service.ArmazenamentoS3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/uploads")
@AllArgsConstructor
public class UploadController {

    private final ArmazenamentoS3Service armazenamentoS3Service;

    @Operation(
            summary = "Upload de Documento",
            description = "Método para realizar o upload de documentos."
    )
    @PostMapping("/documentos")
    public UploadResponse novoDocumentoUpload(
            @Parameter(description = "Arquivo a ser enviado",
                    content = @Content(mediaType = "multipart/form-data",
                            schema = @Schema(type = "string", format = "binary")))
            @RequestParam("arquivo") MultipartFile arquivo) {
        return this.armazenamentoS3Service.uploadDocumento(arquivo, "documento");
    }

    @Operation(
            summary = "Upload de Imagem",
            description = "Método para realizar o upload de imagens."
    )
    @PostMapping("/imagens")
    public UploadResponse novaImagemUpload(
            @Parameter(description = "Arquivo de imagem a ser enviado",
                    content = @Content(mediaType = "multipart/form-data",
                            schema = @Schema(type = "string", format = "binary")))
            @RequestParam("arquivo") MultipartFile arquivo) {
        return this.armazenamentoS3Service.uploadImagem(arquivo, "imagem");
    }
}
