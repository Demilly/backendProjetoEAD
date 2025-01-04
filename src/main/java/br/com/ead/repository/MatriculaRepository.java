package br.com.ead.repository;

import br.com.ead.model.entity.ensino.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
}
