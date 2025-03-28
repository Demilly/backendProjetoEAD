package br.com.ead.service.impl;

import br.com.ead.controller.request.QuestaoRequest;
import br.com.ead.controller.response.ensino.modulo.questao.QuestaoResponse;
import br.com.ead.model.entity.ensino.modulo.Questao;
import br.com.ead.model.entity.ensino.modulo.Resposta;
import br.com.ead.model.mapper.QuestaoMapper;
import br.com.ead.repository.QuestaoRepository;
import br.com.ead.repository.RespostaRepository;
import br.com.ead.service.QuestaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestaoServiceImpl implements QuestaoService {

    private final QuestaoRepository questaoRepository;
    private final QuestaoMapper questaoMapper;
    private final RespostaRepository respostaRepository;

    @Override
    public QuestaoResponse criarQuestao(QuestaoRequest questaoRequest) {
        Questao questaoNaoSalva = questaoMapper.toQuestao(questaoRequest);

        Questao novaQuestao = questaoRepository.save(questaoNaoSalva);
        Set<Resposta> respostas = questaoRequest.getRespostas().stream()
                .map(resposta -> {
                    Resposta novaResposta = new Resposta();
                    novaResposta.setDescricao(resposta.getDescricao());
                    novaResposta.setCorreta(resposta.isCorreta());
                    novaResposta.setQuestao(novaQuestao);
                    return respostaRepository.save(novaResposta);
                }).collect(Collectors.toSet());

        novaQuestao.setRespostas(respostas);

        return questaoMapper.toQuestaoResponse(novaQuestao);
    }

    @Override
    public List<QuestaoResponse> listarTodasQuestoes() {
        List<Questao> questoes = questaoRepository.findAll();
        return questoes.stream()
                .map(questaoMapper::toQuestaoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public QuestaoResponse atualizarQuestao(String uuid, QuestaoRequest questaoRequest) {
        return questaoRepository.findByUuid(uuid)
                .map(questao -> {
                    questao.setPergunta(questaoRequest.getPergunta());
                    questao.setDescricao(questaoRequest.getDescricao());
                    questao.setExplicacao(questaoRequest.getExplicacao());
                    questao.setPontuacao(questaoRequest.getPontuacao());

                    Questao questaoSalva = questaoRepository.save(questao);
                    return questaoMapper.toQuestaoResponse(questaoSalva);
                })
                .orElseThrow(() -> new RuntimeException("Questão não encontrada"));
    }

    @Override
    public boolean deletarQuestao(String uuid) {
        return questaoRepository.findByUuid(uuid).map(questao -> {
            questaoRepository.delete(questao);
            return true;
        }).orElse(false);
    }
}
