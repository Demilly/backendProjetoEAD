package br.com.ead.service;

import br.com.ead.controller.request.QuestaoRequest;
import br.com.ead.controller.response.ensino.modulo.questao.QuestaoResponse;
import br.com.ead.model.entity.ensino.modulo.Questao;

import java.util.List;

public interface QuestaoService {

    QuestaoResponse criarQuestao(QuestaoRequest questaoRequest);

    List<QuestaoResponse> listarTodasQuestoes();

    QuestaoResponse atualizarQuestao(String uuid, QuestaoRequest questaoRequest);

    boolean deletarQuestao(String uuid);
}
