package br.com.ead.controller.response;

import br.com.ead.controller.response.ensino.CursoResponse;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MatriculaResponse {

    private UsuarioResponse usuario;
    private CursoResponse curso;
    private LocalDateTime dataMatricula;
}
