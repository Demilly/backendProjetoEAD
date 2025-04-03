package br.com.ead.service.impl;

import br.com.ead.controller.request.instituicao.InstituicaoRequest;
import br.com.ead.controller.response.instituicao.InstituicaoResponse;
import br.com.ead.model.entity.instituicao.Instituicao;
import br.com.ead.model.mapper.InstituicaoMapper;
import br.com.ead.repository.CursoRepository;
import br.com.ead.repository.InstituicaoRepository;
import br.com.ead.service.InstituicaoService;
import br.com.ead.service.exception.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@Service
public class InstituicaoServiceImpl implements InstituicaoService {

    private final InstituicaoRepository instituicaoRepository;
    private final InstituicaoMapper instituicaoMapper;
    private final CursoRepository cursoRepository;

    @Override
    public List<InstituicaoResponse> listarTodasInstituicoes() {
        List<Instituicao> instituicoes = instituicaoRepository.findAll();
        return instituicoes.stream()
                .map(instituicaoMapper::toInstituicaoResponse)
                .toList();
    }

    @Override
    public InstituicaoResponse salvarInstituicao(InstituicaoRequest instituicaoRequest) {
        var instituicao = instituicaoMapper.toInstituicao(instituicaoRequest);
        var curso = cursoRepository.findByUuid(instituicaoRequest.getUuidCurso())
                .orElseThrow(() -> new BusinessException("Curso não encontrado"));

        instituicao.getCursos().add(curso);
        var instituicaoSalva = instituicaoRepository.save(instituicao);
        return instituicaoMapper.toInstituicaoResponse(instituicaoSalva);
    }

    @Override
    public InstituicaoResponse atualizarInstituicao(String cpfOuCnpj, InstituicaoRequest instituicaoRequest) {
        var instituicaoExistente = instituicaoRepository.findByCpfOuCnpj(cpfOuCnpj)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Instituição não encontrada"));

        instituicaoMapper.updateInstituicaoFromRequest(instituicaoRequest, instituicaoExistente);

        if (instituicaoRequest.getUuidCurso() != null) {
            var curso = cursoRepository.findByUuid(instituicaoRequest.getUuidCurso())
                    .orElseThrow(() -> new BusinessException("Curso não encontrado"));

            instituicaoExistente.getCursos().add(curso);
        }

        var instituicaoAtualizada = instituicaoRepository.save(instituicaoExistente);
        return instituicaoMapper.toInstituicaoResponse(instituicaoAtualizada);
    }

    @Override
    public void deletarInstituicao(String cpfCnpj) {
        var instituicao = instituicaoRepository.findByCpfOuCnpj(cpfCnpj)
                .orElseThrow(() -> new EntityNotFoundException("Instituição não encontrada"));

        instituicao.getCursos().forEach(curso -> curso.getInstituicoes().remove(instituicao));

        instituicaoRepository.delete(instituicao);
    }
}
