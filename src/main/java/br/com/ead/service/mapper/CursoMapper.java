package br.com.ead.service.mapper;

import br.com.ead.controller.request.CursoRequest;
import br.com.ead.model.entity.ensino.Curso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CursoMapper {

    @Mapping(target = "modulos", ignore = true)
    @Mapping(target = "idCurso", ignore = true)
    @Mapping(target = "isAtivo", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "instituicao", ignore = true)
    Curso toCurso(CursoRequest cursoRequest);
}
