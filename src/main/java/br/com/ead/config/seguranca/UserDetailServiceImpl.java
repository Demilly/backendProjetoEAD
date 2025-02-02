package br.com.ead.config.seguranca;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service  // Anote com @Service para que o Spring crie o bean automaticamente
public class UserDetailServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Aqui você vai buscar o usuário, por exemplo, no banco de dados
        // Exemplo simples:
        if ("user".equals(username)) {
            return User.builder()
                    .username("user")
                    .password("{noop}password")  // {noop} indica que a senha não é criptografada
                    .roles("USER")
                    .build();
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
    }
}