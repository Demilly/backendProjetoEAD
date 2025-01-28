package br.com.ead.controller;

import br.com.ead.controller.response.ensino.aula.ProgressaoAulaResponse;
import br.com.ead.service.ProgressaoAulaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/progressoes")
public class ProgressaoAulaController {

    private final ProgressaoAulaService progressaoAulaService;

    @GetMapping("/aluno/{usuarioId}")
    public ResponseEntity<List<ProgressaoAulaResponse>> listarProgressaoPorAluno(@PathVariable Long usuarioId) {
        List<ProgressaoAulaResponse> progressos = progressaoAulaService.listarProgressaoPorAluno(usuarioId);
        return ResponseEntity.ok(progressos);
    }
}
