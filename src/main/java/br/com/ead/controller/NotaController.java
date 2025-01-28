package br.com.ead.controller;

import br.com.ead.controller.request.NotaRequest;
import br.com.ead.controller.response.ensino.modulo.NotaResponse;
import br.com.ead.service.NotaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/notas")
@CrossOrigin("*")
public class NotaController {

    private final NotaService notaService;

    /**
     * Endpoint para salvar uma nova nota.
     *
     * @param notaRequest Dados da nota a ser criada.
     * @return A resposta com os detalhes da nota criada.
     */
    @PostMapping
    public ResponseEntity<NotaResponse> salvarNota(@RequestBody @Valid NotaRequest notaRequest) {
        NotaResponse notaResponse = notaService.salvarNota(notaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(notaResponse);
    }

    /**
     * Endpoint retornar todas as notas por aluno.
     *
     * @param usuarioId Dados da nota a ser criada.
     * @return A NotaResponse com os detalhes da nota criada.
     */
    @GetMapping("/aluno/{usuarioId}")
    public ResponseEntity<List<NotaResponse>> listarNotasPorAluno(@PathVariable Long usuarioId) {
        List<NotaResponse> notas = notaService.listarNotasPorAluno(usuarioId);
        return ResponseEntity.ok(notas);
    }
}

