package br.com.ead.repository;

import br.com.ead.model.entity.ensino.aula.AulaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AulaRepository extends JpaRepository<AulaEntity, Long> {
    Optional<AulaEntity> findByUuid(String uuid);
}
