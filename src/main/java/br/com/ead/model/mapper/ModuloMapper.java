package br.com.ead.model.mapper;

import br.com.ead.controller.request.ModuloRequest;
import br.com.ead.model.entity.ensino.modulo.Modulo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ModuloMapper {

    @Mapping(target = "aulas", ignore = true)
    @Mapping(target = "idModulo", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "curso", ignore = true)
    @Mapping(target = "notas", ignore = true)
    Modulo toModulo(ModuloRequest moduloRequest);
}
