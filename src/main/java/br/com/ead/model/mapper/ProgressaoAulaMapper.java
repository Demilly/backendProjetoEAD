package br.com.ead.model.mapper;

import br.com.ead.controller.response.ensino.aula.ProgressaoAulaResponse;
import br.com.ead.model.entity.ensino.aula.ProgressaoAula;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProgressaoAulaMapper {

    @Mapping(target = "tituloAula", source = "aula.titulo")
    @Mapping(target = "descricaoAula", source = "aula.descricao")
    @Mapping(target = "tituloModulo", source = "aula.modulo.tituloModulo")
    @Mapping(target = "nomeCurso", source = "aula.modulo.curso.nome")
    ProgressaoAulaResponse toProgressaoAulaResponse(ProgressaoAula progressaoAula);
}
