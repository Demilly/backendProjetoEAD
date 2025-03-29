package br.com.ead.repository;

import br.com.ead.model.entity.ensino.modulo.Questao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestaoRepository extends JpaRepository<Questao, Long> {
    Optional<Questao> findByUuid(String uuid);

    Page<Questao> findAllByModuloUuid(String uuidModulo, Pageable pageable);
}

