package br.com.ead.config.seguranca;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "MinhaChaveSecretaSuperSeguraParaJWT";

    // Gera o token JWT
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())  // Defina o 'subject' como o nome de usuário
                .claim("role", userDetails.getAuthorities().iterator().next().getAuthority())  // Aqui pegamos a primeira role do usuário
                .setIssuedAt(new Date())  // Data de emissão
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))  // Expiração do token (1 hora)
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)  // Assinatura com a chave secreta
                .compact();
    }

    // Extraímos o username do token
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Extraímos a role do token (opcional)
    public String extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }
}
