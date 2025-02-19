package br.com.ead.repository;


import br.com.ead.model.entity.s3.ArquivoReferencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArquivoReferenciaRepository extends JpaRepository<ArquivoReferencia, Long> {
}
