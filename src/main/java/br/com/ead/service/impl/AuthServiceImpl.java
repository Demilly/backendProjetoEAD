package br.com.ead.service.impl;

import br.com.ead.config.seguranca.JwtUtil;
import br.com.ead.controller.response.auth.LoginResponse;
import br.com.ead.controller.response.usuario.UsuarioResponse;
import br.com.ead.model.entity.usuario.Usuario;
import br.com.ead.model.mapper.UsuarioMapper;
import br.com.ead.repository.UsuarioRepository;
import br.com.ead.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UsuarioMapper usuarioMapper;

    @Override
    public LoginResponse authenticate(String email, String senha) {
        // Realiza a autenticação do usuário
        authenticateUser(email, senha);

        // Carrega o usuário e gera o token JWT
        Usuario usuario = findUserByEmail(email);
        String token = generateJwtToken(usuario);

        // Cria a resposta de login
        UsuarioResponse usuarioResponse = usuarioMapper.toUsuarioResponse(usuario);
        return new LoginResponse(usuarioResponse.getEmail(), token, usuario.getTipoUsuario(), usuarioResponse);
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        // Carrega o usuário uma única vez
        Usuario usuario = findUserByEmail(email);

        // Retorna os detalhes do usuário para autenticação
        return createUserDetails(usuario);
    }

    @Override
    public void register(Usuario usuario) {
        // Criptografa a senha e registra o usuário
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);
    }

    // Método auxiliar para autenticação do usuário
    private void authenticateUser(String email, String senha) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, senha));
    }

    // Método auxiliar para encontrar o usuário no banco de dados
    private Usuario findUserByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + email));
    }

    // Método auxiliar para gerar o token JWT
    private String generateJwtToken(Usuario usuario) {
        UserDetails userDetails = createUserDetails(usuario);
        return jwtUtil.generateToken(userDetails);
    }

    // Método auxiliar para criar UserDetails a partir do objeto Usuario
    private UserDetails createUserDetails(Usuario usuario) {
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .roles(usuario.getTipoUsuario().name())  // Usa o TipoUsuarioEnum diretamente
                .build();
    }
}
