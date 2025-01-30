package br.com.ead.service.impl;

import br.com.ead.controller.request.ensino.matricula.MatriculaRequest;
import br.com.ead.model.entity.ensino.Matricula;
import br.com.ead.model.entity.usuario.Usuario;
import br.com.ead.repository.CursoRepository;
import br.com.ead.repository.MatriculaRepository;
import br.com.ead.repository.UsuarioRepository;
import br.com.ead.service.MatriculaService;
import br.com.ead.service.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class MatriculaServiceImpl implements MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;

    @Override
    public Matricula efetuarMatricula(MatriculaRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        var curso = cursoRepository.findById(request.getCursoId())
                .orElseThrow(() -> new BusinessException("Curso não encontrado"));

        Matricula matricula = new Matricula();
        matricula.setUsuario(usuario);
        matricula.setCurso(curso);
        matricula.setDataMatricula(LocalDateTime.now());

        return matriculaRepository.save(matricula);
    }
}
