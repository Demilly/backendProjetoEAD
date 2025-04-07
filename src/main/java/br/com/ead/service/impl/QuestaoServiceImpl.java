package br.com.ead.service.impl;

import br.com.ead.controller.request.QuestaoRequest;
import br.com.ead.controller.request.RespostaRequest;
import br.com.ead.controller.response.ensino.modulo.questao.QuestaoResponse;
import br.com.ead.model.entity.ensino.modulo.Questao;
import br.com.ead.model.entity.ensino.modulo.Resposta;
import br.com.ead.model.mapper.QuestaoMapper;
import br.com.ead.repository.ModuloRepository;
import br.com.ead.repository.QuestaoRepository;
import br.com.ead.service.QuestaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestaoServiceImpl implements QuestaoService {

    private final QuestaoRepository questaoRepository;
    private final QuestaoMapper questaoMapper;
    private final ModuloRepository moduloRepository;

    @Override
    @Transactional
    public QuestaoResponse criarQuestao(QuestaoRequest questaoRequest) {
        Questao questaoNaoSalva = questaoMapper.toQuestao(questaoRequest);

        var modulo = moduloRepository.findByUuid(questaoRequest.getUuidModulo()).orElseThrow();
        questaoNaoSalva.setModulo(modulo);

        if (questaoNaoSalva.getRespostas() != null) {
            for (Resposta resposta : questaoNaoSalva.getRespostas()) {
                resposta.setQuestao(questaoNaoSalva);
            }
        }

        Questao novaQuestao = questaoRepository.save(questaoNaoSalva);
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
    public Page<QuestaoResponse> listarQuestoesPaginada(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Questao> questoesPaginados = questaoRepository.findAll(pageable);
        return questoesPaginados.map(questaoMapper::toQuestaoResponse);
    }

    @Override
    public Page<QuestaoResponse> listarQuestoesPaginadaPorModulo(String uuidModulo, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Questao> questoesPaginados = questaoRepository.findAllByModuloUuid(uuidModulo, pageable);
        return questoesPaginados.map(questaoMapper::toQuestaoResponse);
    }

    @Override
    public QuestaoResponse atualizarQuestao(String uuid, QuestaoRequest questaoRequest) {
        return questaoRepository.findByUuid(uuid)
                .map(questao -> {
                    questao.setPergunta(questaoRequest.getPergunta());
                    questao.setDescricao(questaoRequest.getDescricao());
                    questao.setExplicacao(questaoRequest.getExplicacao());
                    questao.setPontuacao(questaoRequest.getPontuacao());

                    // Trabalhar com o Set retornado da entidade, sem substituir
                    Set<Resposta> respostas = questao.getRespostas();
                    respostas.clear();

                    // Adicionar novas respostas
                    for (RespostaRequest respostaRequest : questaoRequest.getRespostas()) {
                        Resposta resposta = new Resposta();
                        resposta.setCorreta(respostaRequest.isCorreta());
                        resposta.setDescricao(respostaRequest.getDescricao());
                        resposta.setOpcao(respostaRequest.getOpcao());
                        resposta.setQuestao(questao);
                        respostas.add(resposta);
                    }

                    var modulo = moduloRepository.findByUuid(questaoRequest.getUuidModulo()).orElseThrow();
                    questao.setModulo(modulo);

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
