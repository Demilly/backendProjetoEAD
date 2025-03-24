package br.com.ead.controller;

import br.com.ead.controller.request.login.UsuarioLoginRequest;
import br.com.ead.repository.UsuarioRepository;
import br.com.ead.service.seguranca.AuthenticationService;
import br.com.ead.service.seguranca.config.model.AuthenticationResponse;
import br.com.ead.service.seguranca.config.model.ErrorResponse;
import br.com.ead.service.seguranca.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioLoginRequest loginRequest) {
        try {
            AuthenticationResponse response = authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getSenha());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Credenciais inválidas", e.getMessage()));
        }
    }
}


