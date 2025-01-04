package br.com.ead.service;

import br.com.ead.controller.request.MatriculaRequest;
import br.com.ead.model.entity.ensino.Matricula;

public interface MatriculaService {

    Matricula efetuarMatricula(MatriculaRequest request);
}
