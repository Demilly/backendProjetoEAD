package br.com.ead.controller;


import br.com.ead.controller.request.usuario.UsuarioRequest;
import br.com.ead.controller.response.UsuarioResponse;
import br.com.ead.service.UsuarioService;
import br.com.ead.service.mapper.UsuarioMapper;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping("/salvar")
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso")
    public ResponseEntity<UsuarioResponse> salvarUsuario(@RequestBody UsuarioRequest usuarioRequest) {
        var usuario = usuarioMapper.toUsuario(usuarioRequest);
        var usuarioSalvo = usuarioService.salvarUsuario(usuario);
        return new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED);
    }
}
