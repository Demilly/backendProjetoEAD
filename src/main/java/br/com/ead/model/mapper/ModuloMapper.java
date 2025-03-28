package br.com.ead.model.mapper;

import br.com.ead.controller.request.ensino.modulo.ModuloRequest;
import br.com.ead.controller.response.ensino.modulo.ModuloResponse;
import br.com.ead.model.entity.ensino.modulo.Modulo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ModuloMapper {

    @Mapping(target = "idModulo", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "curso", ignore = true)
    @Mapping(target = "notas", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "aulas", ignore = true)
    @Mapping(target = "aulas[].progressaoAulas", ignore = true)
    Modulo toModulo(ModuloRequest moduloRequest);

    @Mapping(target = "dataCriacao", source = "dataCriacao")
    @Mapping(target = "dataAtualizacao", source = "dataAtualizacao")
    @Mapping(target = "aulas", ignore = true)
    @Mapping(target = "aulas[].progressaoAulas", ignore = true)
    ModuloResponse toModuloResponse(Modulo modulo);
}
