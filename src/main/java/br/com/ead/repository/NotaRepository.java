package br.com.ead.repository;

import br.com.ead.model.entity.ensino.modulo.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotaRepository extends JpaRepository<Nota, Long> {

    @Query("""
                SELECT n 
                FROM Nota n 
                JOIN n.modulo m 
                JOIN m.curso c 
                JOIN c.matriculas mat 
                WHERE mat.usuario.id = :usuarioId
            """)
    List<Nota> findNotasByUsuarioId(Long usuarioId);
}
