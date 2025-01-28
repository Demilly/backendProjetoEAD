package br.com.ead.repository;

import br.com.ead.model.entity.ensino.aula.ProgressaoAula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProgressaoAulaRepository extends JpaRepository<ProgressaoAula, Long> {

    @Query("""
            SELECT p 
            FROM ProgressaoAula p 
            JOIN p.aula a
            JOIN a.modulo m
            JOIN m.curso c
            WHERE c.usuario.id = :usuarioId
            """)
    List<ProgressaoAula> findProgressaoAulasByUsuarioId(@Param("usuarioId") Long usuarioId);
}
