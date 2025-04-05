package br.com.ead.model.mapper;

import br.com.ead.controller.request.ensino.aula.AulaRequest;
import br.com.ead.controller.response.ensino.aula.AulaResponse;
import br.com.ead.model.entity.ensino.aula.AulaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProgressaoAulaMapper.class)
public interface AulaMapper {

    @Mapping(target = "videos", ignore = true)
    @Mapping(target = "modulo", ignore = true)
    @Mapping(target = "idAula", ignore = true)
    @Mapping(target = "comentarios", ignore = true)
    @Mapping(target = "progressaoAulas", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    AulaEntity toAula(AulaRequest aulaRequest);


    AulaResponse toAulaResponse(AulaEntity aulaEntity);
}
