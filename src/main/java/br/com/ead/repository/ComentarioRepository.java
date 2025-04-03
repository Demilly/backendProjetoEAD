package br.com.ead.repository;

import br.com.ead.model.entity.ensino.aula.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
}
