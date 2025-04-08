package br.com.ead.service.impl;

import br.com.ead.controller.request.ensino.matricula.MatriculaRequest;
import br.com.ead.model.entity.ensino.Curso;
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
import java.util.Optional;

@AllArgsConstructor
@Service
public class MatriculaServiceImpl implements MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;

    @Override
    public Matricula efetuarMatricula(MatriculaRequest request) {
        Usuario usuario = usuarioRepository.findByUuid(request.getUuidUsuario())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        Curso curso = cursoRepository.findByUuid(request.getUuidCurso())
                .orElseThrow(() -> new BusinessException("Curso não encontrado"));

        Optional<Matricula> matriculaExistente = matriculaRepository.findByUsuarioAndCurso(usuario, curso);
        if (matriculaExistente.isPresent()) {
            throw new BusinessException("Usuário já está matriculado neste curso");
        }

        Matricula matricula = new Matricula();
        matricula.setUsuario(usuario);
        matricula.setCurso(curso);
        matricula.setUsuarioUuid(usuario.getUuid());
        matricula.setCursoUuid(curso.getUuid());
        matricula.setDataMatricula(LocalDateTime.now());

        return matriculaRepository.save(matricula);
    }

    @Override
    public void cancelarMatricula(String uuidUsuario, String uuidCurso) {

        Matricula matricula = matriculaRepository.findByUsuarioUuidAndCursoUuid(uuidUsuario, uuidCurso)
                .orElseThrow(() -> new BusinessException("Matrícula não encontrada para cancelamento"));

        matriculaRepository.delete(matricula);
    }
}
