package br.com.ead.controller;


import br.com.ead.controller.request.instituicao.InstituicaoRequest;
import br.com.ead.controller.request.usuario.UsuarioRequest;
import br.com.ead.controller.request.usuario.UsuarioUpdateRequest;
import br.com.ead.controller.response.instituicao.InstituicaoResponse;
import br.com.ead.controller.response.usuario.UsuarioResponse;
import br.com.ead.model.enums.TipoUsuarioEnum;
import br.com.ead.service.UsuarioService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping(value = "/tipo/{tipoUsuarioEnum}")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    public ResponseEntity<Page<UsuarioResponse>> buscarPorTipoUsuarioPaginado(
            @PathVariable TipoUsuarioEnum tipoUsuarioEnum,
            Pageable pageable) {
        Page<UsuarioResponse> usuariosResponse = usuarioService.buscarPorTipoUsuarioPaginado(tipoUsuarioEnum, pageable);
        return ResponseEntity.ok(usuariosResponse);
    }

    @PostMapping("/salvar")
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso")
    public ResponseEntity<UsuarioResponse> salvarUsuario(@RequestBody @Valid UsuarioRequest usuarioRequest) {
        var usuarioSalvo = usuarioService.salvarUsuario(usuarioRequest);
        return new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED);
    }

    @PutMapping("/atualizar/{cpfOuCnpj}")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
    public ResponseEntity<UsuarioResponse> atualizarUsuario(
            @PathVariable String cpfOuCnpj,
            @RequestBody @Valid UsuarioUpdateRequest usuarioUpdateRequest) {

        var instituicaoAtualizada = usuarioService.atualizarInstituicao(cpfOuCnpj, usuarioUpdateRequest);
        return ResponseEntity.ok(instituicaoAtualizada);
    }

    @DeleteMapping("/{cpfOuCnpj}")
    @ApiResponse(responseCode = "204", description = "Usuario deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuario não encontrado")
    public ResponseEntity<Void> deletarUsuario(@PathVariable String cpfOuCnpj) {
        usuarioService.deletarUsuario(cpfOuCnpj);
        return ResponseEntity.noContent().build();
    }

}
