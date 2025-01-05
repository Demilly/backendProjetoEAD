package br.com.ead.controller;


import br.com.ead.controller.request.UsuarioRequest;
import br.com.ead.controller.response.UsuarioResponse;
import br.com.ead.service.UsuarioService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    public ResponseEntity<UsuarioResponse> buscarUsuarioPorId(@PathVariable Long id) {
        var usuario = usuarioService.buscarUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/email/{email}")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    public ResponseEntity<UsuarioResponse> buscarUsuarioPorEmail(@PathVariable String email) {
        var usuario = usuarioService.buscarUsuarioPorEmail(email);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/salvar")
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso")
    public ResponseEntity<UsuarioResponse> salvarUsuario(@RequestBody @Valid UsuarioRequest usuarioRequest) {
        var usuarioSalvo = usuarioService.salvarUsuario(usuarioRequest);
        return new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED);
    }
}
