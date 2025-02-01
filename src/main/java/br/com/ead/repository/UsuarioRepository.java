package br.com.ead.repository;

import br.com.ead.model.entity.usuario.Usuario;
import br.com.ead.model.enums.TipoUsuarioEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Page<Usuario> findByTipoUsuario(TipoUsuarioEnum tipoUsuario, Pageable pageable);

    Optional<Usuario> findByCpfOuCnpj(String cpfOuCnpj);
}
