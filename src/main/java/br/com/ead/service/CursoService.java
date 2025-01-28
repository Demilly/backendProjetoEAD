package br.com.ead.service;

import br.com.ead.controller.request.CursoRequest;
import br.com.ead.controller.response.ensino.CursoResponse;
import org.springframework.data.domain.Page;

public interface CursoService {

    Page<CursoResponse> listarCursos(int page, int size);

    CursoResponse cadastrarCursoComModulos(CursoRequest cursoRequest);

    CursoResponse buscarCursoPorId(Long id);

    void deletarCurso(Long id);

    CursoResponse atualizarCurso(Long id, CursoRequest cursoRequest);
}
