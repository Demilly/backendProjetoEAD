package br.com.ead.model.mapper;

import br.com.ead.controller.request.ensino.curso.CursoRequest;
import br.com.ead.controller.response.ensino.curso.CursoResponse;
import br.com.ead.model.entity.ensino.Curso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CursoMapper {

    @Mapping(target = "modulos", ignore = true)
    @Mapping(target = "idCurso", ignore = true)
    @Mapping(target = "isAtivo", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "instituicao", ignore = true)
    Curso toCurso(CursoRequest cursoRequest);

    CursoResponse toCursoResponse(Curso curso);

    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "modulos", ignore = true)
    @Mapping(target = "instituicao", ignore = true)
    @Mapping(target = "idCurso", ignore = true)
    @Mapping(target = "isAtivo", ignore = true)
    void updateCursoFromRequest(CursoRequest request, @MappingTarget Curso curso);
}
