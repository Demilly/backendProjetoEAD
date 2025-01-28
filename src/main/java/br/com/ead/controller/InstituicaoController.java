package br.com.ead.controller;

import br.com.ead.controller.request.InstituicaoRequest;
import br.com.ead.controller.response.instituicao.InstituicaoResponse;
import br.com.ead.service.InstituicaoService;
import br.com.ead.model.mapper.InstituicaoMapper;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/instituicoes")
public class InstituicaoController {

    private final InstituicaoService instituicaoService;
    private final InstituicaoMapper instituicaoMapper;

    @PostMapping("/salvar")
    @ApiResponse(responseCode = "201", description = "Instituicao criada com sucesso")
    public ResponseEntity<InstituicaoResponse> salvarUsuario(@RequestBody @Valid InstituicaoRequest instituicaoRequest) {
        var instituicao = instituicaoMapper.toInstituicao(instituicaoRequest);
        var instituiocaoSalva = instituicaoService.salvarInstituicao(instituicao);
        return new ResponseEntity<>(instituiocaoSalva, HttpStatus.CREATED);
    }

    @PutMapping("/atualizar/{id}")
    @ApiResponse(responseCode = "200", description = "Instituicao atualizada com sucesso")
    public ResponseEntity<InstituicaoResponse> atualizarInstituicao(@PathVariable Long id, @RequestBody @Valid InstituicaoRequest instituicaoRequest) {
        var instituicaoAtualizada = instituicaoService.atualizarInstituicao(id, instituicaoRequest);
        return ResponseEntity.ok(instituicaoAtualizada);
    }
}
