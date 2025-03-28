package br.com.ead.controller;

import br.com.ead.controller.request.ensino.modulo.ModuloRequest;
import br.com.ead.controller.request.ensino.modulo.UpdateModuloRequest;
import br.com.ead.controller.response.ensino.modulo.ModuloResponse;
import br.com.ead.service.ModuloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/modulos")
public class ModuloController {

    private final ModuloService moduloService;

    @GetMapping("/listar-por-curso/{uuidCurso}")
    @ApiResponse(responseCode = "200", description = "Lista de Modulo retornada com sucesso")
    public ResponseEntity<Page<ModuloResponse>> listarModuloPaginadaPorCurso(
            @PathVariable String uuidCurso,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var modulosPaginada = moduloService.listarModuloPaginadaPorCurso(uuidCurso, page, size);
        return ResponseEntity.ok(modulosPaginada);
    }

    @GetMapping("/listar-por-uuid-curso/{uuidCurso}")
    @ApiResponse(responseCode = "200", description = "Lista de Modulo retornada com sucesso")
    public ResponseEntity<List<ModuloResponse>> listarModuloPorUuidCurso(
            @PathVariable String uuidCurso) {
        var modulosPaginada = moduloService.listarModuloPorUuidCurso(uuidCurso);
        return ResponseEntity.ok(modulosPaginada);
    }

    @GetMapping("/paginada")
    @ApiResponse(responseCode = "200", description = "Lista de Modulo retornada com sucesso")
    public ResponseEntity<Page<ModuloResponse>> listarModuloPaginada(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var modulosPaginada = moduloService.listarModuloPaginada(page, size);
        return ResponseEntity.ok(modulosPaginada);
    }

    @Operation(
            summary = "Cadastrar Módulo com múltiplos arquivos",
            description = "Método para cadastrar um novo módulo com arquivos opcionais."
    )
    @PostMapping(value = "/salvar", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ModuloResponse> cadastrarModulo(
            @RequestPart(value = "moduloRequest") @Valid ModuloRequest moduloRequest,
            @RequestPart(value = "arquivos", required = false) List<MultipartFile> arquivos) {

        ModuloResponse novoModulo = moduloService.cadastrarModulo(moduloRequest, arquivos);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoModulo);
    }

    @PutMapping("/atualizar/{uuid}")
    @ApiResponse(responseCode = "200", description = "Modulo atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Modulo não encontrado")
    public ResponseEntity<ModuloResponse> atualizarModulo(
            @PathVariable String uuid,
            @RequestPart(value = "updateModuloRequest")  @Valid UpdateModuloRequest updateModuloRequest,
            @RequestPart(value = "arquivo", required = false) List<MultipartFile> arquivos){
        ModuloResponse moduloResponse = moduloService.atualizarModulo(uuid, updateModuloRequest, arquivos);
        return ResponseEntity.ok(moduloResponse);
    }

    @DeleteMapping("/{uuid}")
    @ApiResponse(responseCode = "204", description = "Modulo deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Modulo não encontrado")
    public ResponseEntity<Void> deletarModulo(@PathVariable String uuid) {
        moduloService.deletarModulo(uuid);
        return ResponseEntity.noContent().build();
    }
}
