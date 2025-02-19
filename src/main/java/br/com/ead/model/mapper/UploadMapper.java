package br.com.ead.model.mapper;

import br.com.ead.controller.request.UploadDocumentoRequest;
import br.com.ead.controller.request.UploadImagemRequest;
import br.com.ead.model.entity.s3.ArquivoReferencia;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UploadMapper {

    ArquivoReferencia toArquivoReferencia(UploadDocumentoRequest uploadDocumentoRequest);

    ArquivoReferencia toArquivoReferencia(UploadImagemRequest uploadImagemRequest);
}
