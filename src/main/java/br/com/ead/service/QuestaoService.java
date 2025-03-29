package br.com.ead.service;

import br.com.ead.controller.request.QuestaoRequest;
import br.com.ead.controller.response.ensino.modulo.questao.QuestaoResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface QuestaoService {

    QuestaoResponse criarQuestao(QuestaoRequest questaoRequest);

    List<QuestaoResponse> listarTodasQuestoes();

    Page<QuestaoResponse> listarQuestoesPaginada(int page, int size);

    Page<QuestaoResponse> listarQuestoesPaginadaPorModulo(String uuidModulo, int page, int size);

    QuestaoResponse atualizarQuestao(String uuid, QuestaoRequest questaoRequest);

    boolean deletarQuestao(String uuid);
}
