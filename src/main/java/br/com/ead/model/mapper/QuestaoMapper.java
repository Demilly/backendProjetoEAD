package br.com.ead.model.mapper;

import br.com.ead.controller.request.QuestaoRequest;
import br.com.ead.model.entity.ensino.modulo.Questao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuestaoMapper {

    @Mapping(target = "idQuestao", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "aula", ignore = true)
    @Mapping(target = "comentarios", ignore = true)
    Questao toQuestao(QuestaoRequest questaoRequest);
}
