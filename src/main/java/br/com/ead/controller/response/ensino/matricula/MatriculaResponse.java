package br.com.ead.controller.response.ensino.matricula;

import br.com.ead.controller.response.ensino.curso.CursoResponse;
import br.com.ead.controller.response.usuario.UsuarioResponse;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MatriculaResponse {

    private UsuarioResponse usuario;
    private CursoResponse curso;
    private LocalDateTime dataMatricula;
}
