package br.com.ead.controller;


import br.com.ead.controller.request.ensino.curso.CursoRequest;
import br.com.ead.controller.request.instituicao.InstituicaoRequest;
import br.com.ead.controller.request.usuario.UsuarioRequest;
import br.com.ead.controller.request.usuario.UsuarioUpdateRequest;
import br.com.ead.controller.response.ensino.curso.CursoResponse;
import br.com.ead.controller.response.instituicao.InstituicaoResponse;
import br.com.ead.controller.response.usuario.UsuarioResponse;
import br.com.ead.model.enums.TipoUsuarioEnum;
import br.com.ead.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

//    @PostMapping("/salvar")
//    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso")
//    public ResponseEntity<UsuarioResponse> salvarUsuario(@RequestBody @Valid UsuarioRequest usuarioRequest) {
//        var usuarioSalvo = usuarioService.salvarUsuario(usuarioRequest);
//        return new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED);
//    }

    @Operation(
            summary = "Cadastrar Usuário",
            description = "Método para cadastrar um novo usuário com uma imagem opcional."
    )
    @PostMapping(value = "/salvar", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UsuarioResponse> cadastrarUsuario(
            @RequestPart(value = "usuarioRequest") @Valid UsuarioRequest usuarioRequest,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem) {

        UsuarioResponse novoUsuario = usuarioService.salvarUsuario(usuarioRequest, imagem);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @Operation(
            summary = "Cadastrar Usuário",
            description = "Método para cadastrar um novo usuário com uma imagem opcional."
    )
    @PutMapping(value = "/atualizar/{cpfOuCnpj}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
    public ResponseEntity<UsuarioResponse> atualizarUsuario(
            @PathVariable String cpfOuCnpj,
            @RequestPart(value = "usuarioRequest") @Valid UsuarioUpdateRequest usuarioUpdateRequest,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem) {

        var usuarioAtualizada = usuarioService.atualizarUsuario(cpfOuCnpj, usuarioUpdateRequest, imagem);
        return ResponseEntity.ok(usuarioAtualizada);
    }

    @DeleteMapping("/{cpfOuCnpj}")
    @ApiResponse(responseCode = "204", description = "Usuario deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuario não encontrado")
    public ResponseEntity<Void> deletarUsuario(@PathVariable String cpfOuCnpj) {
        usuarioService.deletarUsuario(cpfOuCnpj);
        return ResponseEntity.noContent().build();
    }

}
