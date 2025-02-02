package br.com.ead.controller;

import br.com.ead.controller.request.seguranca.AuthRequest;
import br.com.ead.controller.response.auth.LoginResponse;
import br.com.ead.model.entity.usuario.Usuario;
import br.com.ead.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody AuthRequest request) {
        LoginResponse loginResponse = authService.authenticate(request.getEmail(), request.getSenha());
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody Usuario usuario) {
        authService.register(usuario);
        return ResponseEntity.ok("Usuário registrado com sucesso");
    }
}
