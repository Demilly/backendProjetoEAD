package br.com.ead.service.impl;

import br.com.ead.controller.request.instituicao.InstituicaoRequest;
import br.com.ead.controller.response.instituicao.InstituicaoResponse;
import br.com.ead.model.entity.ensino.Curso;
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

        List<Curso> cursos = cursoRepository.findAllByUuidIn(instituicaoRequest.getUuidCurso());

        if (cursos.isEmpty()) {
            throw new BusinessException("Nenhum curso encontrado para os UUIDs fornecidos");
        }

        cursos.forEach(curso -> {
            curso.getInstituicoes().add(instituicao);
        });

        instituicao.getCursos().clear();
        instituicao.getCursos().addAll(cursos);

        var instituicaoSalva = instituicaoRepository.save(instituicao);

        return instituicaoMapper.toInstituicaoResponse(instituicaoSalva);
    }


    @Override
    public InstituicaoResponse atualizarInstituicao(String cpfOuCnpj, InstituicaoRequest instituicaoRequest) {
        var instituicaoExistente = instituicaoRepository.findByCpfOuCnpj(cpfOuCnpj)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Instituição não encontrada"));

        instituicaoMapper.updateInstituicaoFromRequest(instituicaoRequest, instituicaoExistente);

        if (instituicaoRequest.getUuidCurso() != null) {
            instituicaoRequest.getUuidCurso().clear();
            List<Curso> cursos = cursoRepository.findAllByUuidIn(instituicaoRequest.getUuidCurso());

            if (cursos.isEmpty()) {
                throw new BusinessException("Nenhum curso encontrado para os UUIDs fornecidos");
            }

            instituicaoExistente.getCursos().clear(); // Remove os cursos antigos
            instituicaoExistente.getCursos().addAll(cursos);
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
