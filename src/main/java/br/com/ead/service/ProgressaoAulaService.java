package br.com.ead.service;

import br.com.ead.controller.response.ensino.aula.ProgressaoAulaResponse;

import java.util.List;

public interface ProgressaoAulaService {
    List<ProgressaoAulaResponse> listarProgressaoPorAluno(Long usuarioId);
}
