package br.com.ead.repository;

import br.com.ead.model.entity.ensino.modulo.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotaRepository extends JpaRepository<Nota, Long> {
}
