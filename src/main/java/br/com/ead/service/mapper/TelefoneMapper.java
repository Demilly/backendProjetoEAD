package br.com.ead.service.mapper;

import br.com.ead.controller.request.usuario.TelefoneRequest;
import br.com.ead.controller.response.TelefoneResponse;
import br.com.ead.model.entity.usuario.Telefone;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = UsuarioMapper.class)
public interface TelefoneMapper {

    Telefone toTelefone(TelefoneRequest telefoneRequest);

    TelefoneResponse toTelefoneResponse(Telefone telefone);

    List<Telefone> toTelefoneRequestList(List<TelefoneRequest> telefoneRequestList);

    List<TelefoneResponse> toTelefoneResponseList(List<Telefone> telefonesList);
}
