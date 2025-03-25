package br.com.ead.repository;

import br.com.ead.model.entity.ensino.modulo.Modulo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ModuloRepository extends JpaRepository<Modulo, Long> {

    Page<Modulo> findByCursoUuid(String uuidCurso, Pageable pageable);

    List<Modulo> findByCursoUuid(String uuidCurso);

    Optional<Modulo> findByUuid(String uuid);
}
