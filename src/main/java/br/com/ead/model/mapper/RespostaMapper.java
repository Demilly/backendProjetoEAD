package br.com.ead.model.mapper;

import br.com.ead.controller.request.RespostaRequest;
import br.com.ead.controller.response.RespostaResponse;
import br.com.ead.model.entity.ensino.modulo.Resposta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RespostaMapper {

    @Mapping(target = "idResposta", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "questao", ignore = true)
    Resposta toResposta(RespostaRequest respostaRequest);

    RespostaResponse toRespostaResponse(Resposta resposta);
}