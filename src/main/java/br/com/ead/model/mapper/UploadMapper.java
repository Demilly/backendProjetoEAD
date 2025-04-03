package br.com.ead.model.mapper;

import br.com.ead.controller.request.UploadDocumentoRequest;
import br.com.ead.controller.request.UploadImagemRequest;
import br.com.ead.model.entity.s3.ArquivoReferencia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UploadMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tipo", ignore = true)
    @Mapping(target = "temp", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    ArquivoReferencia toArquivoReferencia(UploadDocumentoRequest uploadDocumentoRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tipo", ignore = true)
    @Mapping(target = "temp", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    ArquivoReferencia toArquivoReferencia(UploadImagemRequest uploadImagemRequest);
}