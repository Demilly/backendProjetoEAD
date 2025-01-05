package br.com.ead.model.mapper;

import br.com.ead.controller.request.NotaRequest;
import br.com.ead.controller.response.ensino.modulo.NotaResponse;
import br.com.ead.model.entity.ensino.modulo.Nota;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotaMapper {

    @Mapping(target = "idNota", ignore = true)
    @Mapping(target = "modulo", ignore = true)
    Nota toNota(NotaRequest notaRequest);

    NotaResponse toNotaResponse(Nota nota);
}
