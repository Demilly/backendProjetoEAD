package br.com.ead.model.mapper;

import br.com.ead.controller.request.instituicao.InstituicaoRequest;
import br.com.ead.controller.response.instituicao.InstituicaoResponse;
import br.com.ead.model.entity.instituicao.Instituicao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InstituicaoMapper {

    @Mapping(target = "idInstituicao", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "usuarios", ignore = true)
    @Mapping(target = "cursos", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    Instituicao toInstituicao(InstituicaoRequest instituicaoRequest);

    @Mapping(target = "uuidCurso", expression = "java(instituicao.getCursos().stream().map(c -> c.getUuid()).toList())")
    InstituicaoResponse toInstituicaoResponse(Instituicao instituicao);

    @Mapping(target = "idInstituicao", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "usuarios", ignore = true)
    @Mapping(target = "cursos", ignore = true)
    @Mapping(target = "ativa", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    void updateInstituicaoFromRequest(InstituicaoRequest instituicaoRequest, @MappingTarget Instituicao instituicao);
}
