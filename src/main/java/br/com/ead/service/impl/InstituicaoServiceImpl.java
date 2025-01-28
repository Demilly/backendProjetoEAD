package br.com.ead.service.impl;

import br.com.ead.controller.request.InstituicaoRequest;
import br.com.ead.controller.response.instituicao.InstituicaoResponse;
import br.com.ead.model.entity.instituicao.Instituicao;
import br.com.ead.repository.InstituicaoRepository;
import br.com.ead.service.InstituicaoService;
import br.com.ead.model.mapper.InstituicaoMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Service
public class InstituicaoServiceImpl implements InstituicaoService {

    private final InstituicaoRepository instituicaoRepository;
    private final InstituicaoMapper instituicaoMapper;

    @Override
    public InstituicaoResponse salvarInstituicao(Instituicao instituicao) {
        var instituicaoSalva = instituicaoRepository.save(instituicao);
        return instituicaoMapper.toInstituicaoResponse(instituicaoSalva);
    }

    @Override
    public InstituicaoResponse atualizarInstituicao(Long id, InstituicaoRequest instituicaoRequest) {
        var instituicaoExistente = instituicaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instituição não encontrada"));

        instituicaoMapper.updateInstituicaoFromRequest(instituicaoRequest, instituicaoExistente);

        var instituicaoAtualizada = instituicaoRepository.save(instituicaoExistente);
        return instituicaoMapper.toInstituicaoResponse(instituicaoAtualizada);
    }
}
