package br.com.ead.model.mapper;

import br.com.ead.controller.request.ensino.curso.CursoRequest;
import br.com.ead.controller.response.ensino.curso.CursoResponse;
import br.com.ead.model.entity.ensino.Curso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ModuloMapper.class})
public interface CursoMapper {

    @Mapping(target = "modulos", ignore = true)
    @Mapping(target = "idCurso", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "instituicoes", ignore = true)
    @Mapping(target = "matriculas", ignore = true)
    Curso toCurso(CursoRequest cursoRequest);


    @Mapping(source = "modulos", target = "modulos")
    CursoResponse toCursoResponse(Curso curso);
}
