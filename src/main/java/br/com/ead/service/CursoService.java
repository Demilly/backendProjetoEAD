package br.com.ead.service;

import br.com.ead.controller.request.ensino.curso.CursoRequest;
import br.com.ead.controller.request.ensino.curso.UpdateRequest;
import br.com.ead.controller.response.ensino.curso.CursoResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CursoService {

    List<CursoResponse> listarCursos();

    Page<CursoResponse> listarCursosPaginada(int page, int size);

    CursoResponse cadastrarCurso(CursoRequest cursoRequest);

    CursoResponse buscarCursoPorId(Long id);

    void deletarCurso(String uuid);

    CursoResponse atualizarCurso(String uuid, UpdateRequest updateRequest);
}
