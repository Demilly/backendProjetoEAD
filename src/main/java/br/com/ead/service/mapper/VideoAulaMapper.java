package br.com.ead.service.mapper;

import br.com.ead.controller.request.VideoAulaRequest;
import br.com.ead.model.entity.ensino.aula.VideoAula;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VideoAulaMapper {

    @Mapping(target = "idVideoAula", ignore = true)
    @Mapping(target = "aula", ignore = true)
    VideoAula toVideoAula(VideoAulaRequest videoAulaRequest);
}