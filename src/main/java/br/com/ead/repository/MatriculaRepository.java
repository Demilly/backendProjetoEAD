package br.com.ead.repository;

import br.com.ead.model.entity.ensino.Curso;
import br.com.ead.model.entity.ensino.Matricula;
import br.com.ead.model.entity.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    Optional<Matricula> findByUsuarioAndCurso(Usuario usuario, Curso curso);

    Optional<Matricula> findByUsuarioUuidAndCursoUuid(String usuarioUuid, String uuidCurso);
}
