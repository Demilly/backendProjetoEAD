package br.com.ead.controller;

import br.com.ead.controller.request.ensino.aula.AulaRequest;
import br.com.ead.controller.response.ensino.aula.AulaResponse;
import br.com.ead.service.AulaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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


    @GetMapping("/{uuid}")
    public AulaResponse getAula(@PathVariable String uuid) {
        return aulaService.getAula(uuid);
    }

    @GetMapping
    public List<AulaResponse> listarAulas() {
        return aulaService.listarAulas();
    }

    @PutMapping("/{id}")
    public void atualizarAula(@PathVariable Long id, @RequestBody AulaRequest aulaRequest) {
        aulaService.atualizarAula(id, aulaRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirAula(@PathVariable Long id) {
        aulaService.excluirAula(id);
    }
}
