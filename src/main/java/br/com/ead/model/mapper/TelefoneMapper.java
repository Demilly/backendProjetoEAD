package br.com.ead.model.mapper;

import br.com.ead.controller.request.usuario.TelefoneRequest;
import br.com.ead.controller.response.usuario.TelefoneResponse;
import br.com.ead.model.entity.usuario.Telefone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = UsuarioMapper.class)
public interface TelefoneMapper {

    @Mapping(target = "idTelefone", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Telefone toTelefone(TelefoneRequest telefoneRequest);

    TelefoneResponse toTelefoneResponse(Telefone telefone);

    List<Telefone> toTelefoneRequestList(List<TelefoneRequest> telefoneRequestList);

    List<TelefoneResponse> toTelefoneResponseList(List<Telefone> telefonesList);
}
