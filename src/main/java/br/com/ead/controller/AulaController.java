package br.com.ead.controller;

import br.com.ead.controller.request.ensino.aula.AulaRequest;
import br.com.ead.controller.response.ensino.aula.AulaResponse;
import br.com.ead.service.AulaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/aulas")
public class AulaController {

    @Autowired
    private AulaService aulaService;

    @Operation(
            summary = "Cadastrar Aula com múltiplos arquivos",
            description = "Método para cadastrar uma nova aula com arquivos opcionais."
    )
    @PostMapping(value = "/salvar", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public AulaResponse criarAula(@RequestPart(value = "aulaRequest") @Valid AulaRequest aulaRequest,
                                  @RequestPart(value = "arquivos", required = false) List<MultipartFile> arquivos) {
        return aulaService.criarAula(aulaRequest, arquivos);
    }

    @GetMapping("/paginada")
    @ApiResponse(responseCode = "200", description = "Lista de aulas retornada com sucesso")
    public ResponseEntity<Page<AulaResponse>> listarAulasPaginada(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var aulasPaginadas = aulaService.listarAulasPaginada(page, size);
        return ResponseEntity.ok(aulasPaginadas);
    }

    @GetMapping("/listar-por-modulo/{uuidModulo}")
    @ApiResponse(responseCode = "200", description = "Lista de Questoes retornada com sucesso")
    public ResponseEntity<Page<AulaResponse>> listarAulasPaginadaPorModulo(
            @PathVariable String uuidModulo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var aulasPaginadas = aulaService.listarAulasPaginadaPorModulo(uuidModulo, page, size);
        return ResponseEntity.ok(aulasPaginadas);
    }

    @GetMapping("/{uuid}")
    public AulaResponse getAula(@PathVariable String uuid) {
        return aulaService.getAula(uuid);
    }

    @GetMapping
    public List<AulaResponse> listarAulas() {
        return aulaService.listarAulas();
    }

    @PutMapping("/atualizar/{uuid}")
    @ApiResponse(responseCode = "200", description = "Aula atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Aula não encontrada")
    public  ResponseEntity<AulaResponse> atualizarAula(
            @PathVariable String uuid,
            @RequestPart(value = "updateRequest") @Valid AulaRequest aulaRequest,
            @RequestPart(value = "arquivos", required = false) List<MultipartFile> arquivos) {
        AulaResponse aulaAtualizada = aulaService.atualizarAula(uuid, aulaRequest, arquivos);
        return ResponseEntity.ok(aulaAtualizada);
    }

    @DeleteMapping("/{uuid}")
    @ApiResponse(responseCode = "204", description = "Aula deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Aula não encontrada")
    public ResponseEntity<Void> excluirAula(@PathVariable String uuid) {
        aulaService.excluirAula(uuid);
        return ResponseEntity.noContent().build();
    }
}
