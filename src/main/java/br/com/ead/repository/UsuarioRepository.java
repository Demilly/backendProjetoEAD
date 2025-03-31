package br.com.ead.repository;

import br.com.ead.model.entity.usuario.Usuario;
import br.com.ead.model.enums.TipoUsuarioEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Page<Usuario> findByTipoUsuario(TipoUsuarioEnum tipoUsuario, Pageable pageable);

    Optional<Usuario> findByCpfOuCnpj(String cpfOuCnpj);

    @Query("SELECT u FROM Usuario u WHERE u.tipoUsuario IN ('ADMINISTRADOR', 'DIRETOR', 'SUPORTE')  ORDER BY u.nome ASC ")
    Page<Usuario> findAllUsersAdm(Pageable pageable);
}
