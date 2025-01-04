package br.com.ead.repository;

import br.com.ead.model.entity.ensino.modulo.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuloRepository extends JpaRepository<Modulo, Long> {
}
