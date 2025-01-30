package br.com.ead.controller;

import br.com.ead.controller.request.instituicao.InstituicaoRequest;
import br.com.ead.controller.response.instituicao.InstituicaoResponse;
import br.com.ead.service.InstituicaoService;
import br.com.ead.model.mapper.InstituicaoMapper;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/instituicoes")
@CrossOrigin("*")
public class InstituicaoController {

    private final InstituicaoService instituicaoService;
    private final InstituicaoMapper instituicaoMapper;

    @GetMapping
    @ApiResponse(responseCode = "200", description = "Lista de instituições retornada com sucesso")
    public ResponseEntity<List<InstituicaoResponse>> listarInstituicoes() {
        List<InstituicaoResponse> instituicoes = instituicaoService.listarTodasInstituicoes();
        return new ResponseEntity<>(instituicoes, HttpStatus.OK);
    }

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
