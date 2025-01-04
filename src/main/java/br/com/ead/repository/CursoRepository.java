package br.com.ead.repository;

import br.com.ead.model.entity.ensino.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
