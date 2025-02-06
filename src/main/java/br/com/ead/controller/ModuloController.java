package br.com.ead.controller;

import br.com.ead.controller.request.ensino.modulo.ModuloRequest;
import br.com.ead.controller.request.ensino.modulo.UpdateModuloRequest;
import br.com.ead.controller.response.ensino.modulo.ModuloResponse;
import br.com.ead.service.ModuloService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/modulos")
public class ModuloController {

    private final ModuloService moduloService;

    @GetMapping("/listar-por-curso/{uuidCurso}")
    @ApiResponse(responseCode = "200", description = "Lista de Modulo retornada com sucesso")
    public ResponseEntity<List<ModuloResponse>> listarModuloPorCurso(@PathVariable String uuidCurso) {
        var modulos = moduloService.listaModuloPorCurso(uuidCurso);
        return ResponseEntity.ok(modulos);
    }

    @PostMapping("/salvar")
    public ResponseEntity<ModuloResponse> cadastrarModulo(@RequestBody @Valid ModuloRequest moduloRequest) {
        ModuloResponse novoModulo = moduloService.cadastrarModulo(moduloRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoModulo);
    }

    @PutMapping("/atualizar/{uuid}")
    @ApiResponse(responseCode = "200", description = "Modulo atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Modulo não encontrado")
    public ResponseEntity<ModuloResponse> atualizarModulo(
            @PathVariable String uuid,
            @RequestBody UpdateModuloRequest updateModuloRequest){
        ModuloResponse moduloResponse = moduloService.atualizarModulo(uuid, updateModuloRequest);
        return ResponseEntity.ok(moduloResponse);
    }

    @DeleteMapping("/{uuid}")
    @ApiResponse(responseCode = "204", description = "Modulo deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Modulo não encontrado")
    public ResponseEntity<Void> deletarModulo(@PathVariable String uuid) {
        moduloService.deletarCurso(uuid);
        return ResponseEntity.noContent().build();
    }
}
