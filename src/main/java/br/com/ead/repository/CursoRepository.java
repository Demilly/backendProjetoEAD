package br.com.ead.repository;

import br.com.ead.model.entity.ensino.Curso;
import br.com.ead.model.entity.instituicao.Instituicao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    Optional<Curso> findByUuid(String uuid);

    List<Curso> findByInstituicoesContaining(Instituicao instituicao);
}
