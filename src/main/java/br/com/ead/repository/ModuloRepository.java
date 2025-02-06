package br.com.ead.repository;

import br.com.ead.model.entity.ensino.modulo.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ModuloRepository extends JpaRepository<Modulo, Long> {

    List<Modulo> findByCursoUuid(String uuid);

    Optional<Modulo> findByUuid(String uuid);
}
