package br.com.ead.controller;

import br.com.ead.controller.request.CursoRequest;
import br.com.ead.model.entity.ensino.Curso;
import br.com.ead.service.CursoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService cursoService;

    @PostMapping("/modulos")
    public ResponseEntity<Curso> cadastrarCursoComModulos(@RequestBody CursoRequest cursoRequest) {
        Curso novoCurso = cursoService.cadastrarCursoComModulos(cursoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCurso);
    }
}
