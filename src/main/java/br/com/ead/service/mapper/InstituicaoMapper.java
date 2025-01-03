package br.com.ead.service.mapper;

import br.com.ead.controller.request.instituicao.InstituicaoRequest;
import br.com.ead.controller.response.InstituicaoResponse;
import br.com.ead.model.entity.instituicao.Instituicao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InstituicaoMapper {

    @Mapping(target = "idInstituicao", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "usuarios", ignore = true)
    @Mapping(target = "cursos", ignore = true)
    Instituicao toInstituicao(InstituicaoRequest instituicaoRequest);

    InstituicaoResponse toInstituicaoResponse(Instituicao instituicao);
}
