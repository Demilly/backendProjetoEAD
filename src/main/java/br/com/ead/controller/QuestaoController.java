package br.com.ead.controller;

import br.com.ead.controller.request.QuestaoRequest;
import br.com.ead.controller.response.ensino.modulo.questao.QuestaoResponse;
import br.com.ead.service.QuestaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/questoes")
@RequiredArgsConstructor
public class QuestaoController {

    private final QuestaoService questaoService;

    @PostMapping
    public ResponseEntity<QuestaoResponse> criarQuestao(@RequestBody QuestaoRequest questaoRequest) {
        QuestaoResponse questaoCriada = questaoService.criarQuestao(questaoRequest);
        return ResponseEntity.created(URI.create("/questoes/" + questaoCriada.getUuid())).body(questaoCriada);
    }

    @GetMapping
    public ResponseEntity<List<QuestaoResponse>> listarTodasQuestoes() {
        List<QuestaoResponse> questoes = questaoService.listarTodasQuestoes();
        return ResponseEntity.ok(questoes);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<QuestaoResponse> atualizarQuestao(@PathVariable String uuid, @RequestBody QuestaoRequest questaoRequest) {
        QuestaoResponse questaoAtualizada = questaoService.atualizarQuestao(uuid, questaoRequest);
        return ResponseEntity.ok(questaoAtualizada);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deletarQuestao(@PathVariable String uuid) {
        boolean deletado = questaoService.deletarQuestao(uuid);
        if (deletado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
