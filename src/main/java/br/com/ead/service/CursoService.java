package br.com.ead.service;

import br.com.ead.controller.request.CursoRequest;
import br.com.ead.model.entity.ensino.Curso;

public interface CursoService {

    Curso cadastrarCursoComModulos(CursoRequest cursoRequest);
}
