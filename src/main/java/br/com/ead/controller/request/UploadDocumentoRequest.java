package br.com.ead.controller.request;

import br.com.ead.service.validation.AllowedContentTypes;
import br.com.ead.service.validation.AllowedFileExtensions;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UploadDocumentoRequest {

    @NotBlank
    @AllowedFileExtensions("pdf")
    private String nomeArquivo;

    @NotBlank
    @AllowedContentTypes("application/pdf")
    private String contentType;

    @NotNull
    @Min(1)
    private Long contentLength;

}
