package br.com.ead.service.seguranca;

import br.com.ead.model.enums.TipoUsuarioEnum;
import br.com.ead.model.mapper.UsuarioMapper;
import br.com.ead.repository.UsuarioRepository;
import br.com.ead.service.seguranca.config.model.AuthenticationResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public AuthenticationResponse authenticate(String email, String password) throws Exception {
        var usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("Usuário não encontrado"));

        var usuarioResponse = usuarioMapper.toUsuarioResponse(usuario);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        if (authentication.isAuthenticated()) {
            String token = generateToken(email, usuario.getTipoUsuario());
            return new AuthenticationResponse(usuarioResponse, token);
        } else {
            throw new Exception("Credenciais inválidas");
        }
    }

    public String generateToken(String email, TipoUsuarioEnum tipoUsuario) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", tipoUsuario.name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1h
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
