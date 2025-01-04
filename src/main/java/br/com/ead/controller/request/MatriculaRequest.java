package br.com.ead.controller.request;

import lombok.Data;

@Data
public class MatriculaRequest {
    private Long usuarioId;
    private Long cursoId;
}
