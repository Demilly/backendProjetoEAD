package br.com.ead.repository;

import br.com.ead.model.entity.instituicao.Instituicao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstituicaoRepository extends JpaRepository<Instituicao, Long> {

    Optional<Instituicao> findByCpfOuCnpj(String cpfOuCnpj);
}
