package br.com.ead.repository;

import br.com.ead.model.entity.usuario.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
}
