package br.com.ead.service;

import br.com.ead.controller.request.CursoRequest;
import br.com.ead.controller.response.ensino.CursoResponse;

public interface CursoService {

    CursoResponse cadastrarCursoComModulos(CursoRequest cursoRequest);

    CursoResponse buscarCursoPorId(Long id);

    void deletarCurso(Long id);

    CursoResponse atualizarCurso(Long id, CursoRequest cursoRequest);
}
