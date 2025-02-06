package br.com.ead.controller;

import br.com.ead.controller.request.ensino.curso.CursoRequest;
import br.com.ead.controller.request.ensino.curso.UpdateRequest;
import br.com.ead.controller.response.ensino.curso.CursoResponse;
import br.com.ead.service.CursoService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService cursoService;

    @GetMapping("/paginada")
    @ApiResponse(responseCode = "200", description = "Lista de cursos retornada com sucesso")
    public ResponseEntity<Page<CursoResponse>> listarCursosPaginada(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var cursosPaginados = cursoService.listarCursosPaginada(page, size);
        return ResponseEntity.ok(cursosPaginados);
    }

    @GetMapping
    @ApiResponse(responseCode = "200", description = "Lista de cursos retornada com sucesso")
    public ResponseEntity<List<CursoResponse>> listarCursos() {
        var cursosPaginados = cursoService.listarCursos();
        return ResponseEntity.ok(cursosPaginados);
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Curso encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    public ResponseEntity<CursoResponse> buscarUsuarioPorId(@PathVariable Long id) {
        var curso = cursoService.buscarCursoPorId(id);
        return ResponseEntity.ok(curso);
    }

    @PostMapping("/salvar")
    public ResponseEntity<CursoResponse> cadastrarCursoComModulos(@RequestBody @Valid CursoRequest cursoRequest) {
        CursoResponse novoCurso = cursoService.cadastrarCurso(cursoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCurso);
    }

    @DeleteMapping("/{uuid}")
    @ApiResponse(responseCode = "204", description = "Curso deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    public ResponseEntity<Void> deletarCurso(@PathVariable String uuid) {
        cursoService.deletarCurso(uuid);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/atualizar/{uuid}")
    @ApiResponse(responseCode = "200", description = "Curso atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    public ResponseEntity<CursoResponse> atualizarCurso(
            @PathVariable String uuid,
            @RequestBody UpdateRequest updateRequest){
        CursoResponse cursoAtualizado = cursoService.atualizarCurso(uuid, updateRequest);
        return ResponseEntity.ok(cursoAtualizado);
    }

}
