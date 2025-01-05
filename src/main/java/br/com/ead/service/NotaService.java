package br.com.ead.service;

import br.com.ead.controller.request.NotaRequest;
import br.com.ead.controller.response.ensino.modulo.NotaResponse;

public interface NotaService {
    NotaResponse salvarNota(NotaRequest notaRequest);
}
