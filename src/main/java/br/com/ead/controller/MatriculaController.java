package br.com.ead.controller;


import br.com.ead.controller.request.ensino.matricula.MatriculaRequest;
import br.com.ead.model.entity.ensino.Matricula;
import br.com.ead.service.MatriculaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/matriculas")
public class MatriculaController {

    private final MatriculaService matriculaService;

    @PostMapping
    public ResponseEntity<Matricula> efetuarMatricula(@RequestBody MatriculaRequest request) {
        Matricula matricula = matriculaService.efetuarMatricula(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(matricula);
    }
}