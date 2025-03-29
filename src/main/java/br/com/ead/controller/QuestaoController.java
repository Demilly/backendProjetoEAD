package br.com.ead.controller;

import br.com.ead.controller.request.QuestaoRequest;
import br.com.ead.controller.response.ensino.modulo.questao.QuestaoResponse;
import br.com.ead.service.QuestaoService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @GetMapping("/paginada")
    @ApiResponse(responseCode = "200", description = "Lista de questões retornada com sucesso")
    public ResponseEntity<Page<QuestaoResponse>> listarQuestoesPaginada(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var questoesPaginados = questaoService.listarQuestoesPaginada(page, size);
        return ResponseEntity.ok(questoesPaginados);
    }

    @GetMapping("/listar-por-modulo/{uuidModulo}")
    @ApiResponse(responseCode = "200", description = "Lista de Questoes retornada com sucesso")
    public ResponseEntity<Page<QuestaoResponse>> listarQuestoesPaginadaPorModulo(
            @PathVariable String uuidModulo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var questoesPaginadas = questaoService.listarQuestoesPaginadaPorModulo(uuidModulo, page, size);
        return ResponseEntity.ok(questoesPaginadas);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<QuestaoResponse> atualizarQuestao(@PathVariable String uuid,
                                                            @RequestPart(value = "updateRequest") @Valid QuestaoRequest updateRequest) {
        QuestaoResponse questaoAtualizada = questaoService.atualizarQuestao(uuid, updateRequest);
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
