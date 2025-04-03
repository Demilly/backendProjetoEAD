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
            JOIN FETCH p.aula a 
            JOIN FETCH a.modulo m 
            JOIN FETCH m.curso c 
            LEFT JOIN FETCH c.matriculas mat 
            LEFT JOIN FETCH mat.usuario u
            WHERE u.uuid = :uuid
            """)
    List<ProgressaoAula> findProgressaoAulasByUsuarioId(@Param("uuid") String uuid);
}
