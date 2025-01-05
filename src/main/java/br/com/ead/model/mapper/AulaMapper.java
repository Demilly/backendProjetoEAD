package br.com.ead.model.mapper;

import br.com.ead.controller.request.AulaRequest;
import br.com.ead.model.entity.ensino.aula.Aula;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AulaMapper {

    @Mapping(target = "videos", ignore = true)
    @Mapping(target = "modulo", ignore = true)
    @Mapping(target = "questoes", ignore = true)
    @Mapping(target = "idAula", ignore = true)
    @Mapping(target = "comentarios", ignore = true)
    @Mapping(target = "progressaoAulas", ignore = true)
    Aula toAula(AulaRequest aulaRequest);
}
