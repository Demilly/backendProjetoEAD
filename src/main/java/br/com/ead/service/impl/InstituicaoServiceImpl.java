package br.com.ead.service.impl;

import br.com.ead.controller.response.InstituicaoResponse;
import br.com.ead.model.entity.instituicao.Instituicao;
import br.com.ead.repository.InstituicaoRepository;
import br.com.ead.service.InstituicaoService;
import br.com.ead.service.mapper.InstituicaoMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}
