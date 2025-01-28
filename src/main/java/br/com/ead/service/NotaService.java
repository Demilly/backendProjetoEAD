package br.com.ead.service;

import br.com.ead.controller.request.NotaRequest;
import br.com.ead.controller.response.ensino.modulo.NotaResponse;

import java.util.List;

public interface NotaService {
    NotaResponse salvarNota(NotaRequest notaRequest);

    List<NotaResponse> listarNotasPorAluno(Long usuarioId);
}
