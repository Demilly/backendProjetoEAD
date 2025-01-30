package br.com.ead.controller.request.ensino.matricula;

import lombok.Data;

@Data
public class MatriculaRequest {
    private Long usuarioId;
    private Long cursoId;
}
