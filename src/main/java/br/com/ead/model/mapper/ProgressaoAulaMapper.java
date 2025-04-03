package br.com.ead.model.mapper;

import br.com.ead.controller.response.ensino.aula.ProgressaoAulaResponse;
import br.com.ead.model.entity.ensino.aula.ProgressaoAula;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProgressaoAulaMapper {

    @Mapping(target = "tituloAula", ignore = true)
    @Mapping(target = "descricaoAula", ignore = true)
    @Mapping(target = "tituloModulo", ignore = true)
    @Mapping(target = "nomeCurso", ignore = true)
    ProgressaoAulaResponse toProgressaoAulaResponse(ProgressaoAula progressaoAula);
}
