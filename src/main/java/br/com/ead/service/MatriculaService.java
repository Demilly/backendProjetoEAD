package br.com.ead.service;

import br.com.ead.controller.request.ensino.matricula.MatriculaRequest;
import br.com.ead.model.entity.ensino.Matricula;

public interface MatriculaService {

    Matricula efetuarMatricula(MatriculaRequest request);
}
