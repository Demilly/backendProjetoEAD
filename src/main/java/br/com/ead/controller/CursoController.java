package br.com.ead.controller;

import br.com.ead.controller.request.ensino.curso.CursoRequest;
import br.com.ead.controller.response.ensino.curso.CursoResponse;
import br.com.ead.service.CursoService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService cursoService;

    @GetMapping
    @ApiResponse(responseCode = "200", description = "Lista de cursos retornada com sucesso")
    public ResponseEntity<Page<CursoResponse>> listarCursos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var cursosPaginados = cursoService.listarCursos(page, size);
        return ResponseEntity.ok(cursosPaginados);
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Curso encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    public ResponseEntity<CursoResponse> buscarUsuarioPorId(@PathVariable Long id) {
        var curso = cursoService.buscarCursoPorId(id);
        return ResponseEntity.ok(curso);
    }

    @PostMapping("/modulos")
    public ResponseEntity<CursoResponse> cadastrarCursoComModulos(@RequestBody CursoRequest cursoRequest) {
        CursoResponse novoCurso = cursoService.cadastrarCursoComModulos(cursoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCurso);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204", description = "Curso deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    public ResponseEntity<Void> deletarCurso(@PathVariable Long id) {
        cursoService.deletarCurso(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Curso atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    public ResponseEntity<CursoResponse> atualizarCurso(
            @PathVariable Long id,
            @RequestBody CursoRequest cursoRequest) {
        CursoResponse cursoAtualizado = cursoService.atualizarCurso(id, cursoRequest);
        return ResponseEntity.ok(cursoAtualizado);
    }

}
