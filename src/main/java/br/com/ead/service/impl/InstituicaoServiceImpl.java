package br.com.ead.service.impl;

import br.com.ead.controller.request.instituicao.InstituicaoRequest;
import br.com.ead.controller.response.instituicao.InstituicaoResponse;
import br.com.ead.model.entity.instituicao.Instituicao;
import br.com.ead.model.mapper.InstituicaoMapper;
import br.com.ead.repository.InstituicaoRepository;
import br.com.ead.service.InstituicaoService;
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

    @Override
    public List<InstituicaoResponse> listarTodasInstituicoes() {
        List<Instituicao> instituicoes = instituicaoRepository.findAll();
        return instituicoes.stream()
                .map(instituicaoMapper::toInstituicaoResponse)
                .toList();
    }

    @Override
    public InstituicaoResponse salvarInstituicao(Instituicao instituicao) {
        var instituicaoSalva = instituicaoRepository.save(instituicao);
        return instituicaoMapper.toInstituicaoResponse(instituicaoSalva);
    }

    @Override
    public InstituicaoResponse atualizarInstituicao(String cpfOuCnpj, InstituicaoRequest instituicaoRequest) {
        var instituicaoExistente = instituicaoRepository.findByCpfOuCnpj(cpfOuCnpj)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instituição não encontrada"));

        instituicaoMapper.updateInstituicaoFromRequest(instituicaoRequest, instituicaoExistente);

        var instituicaoAtualizada = instituicaoRepository.save(instituicaoExistente);
        return instituicaoMapper.toInstituicaoResponse(instituicaoAtualizada);
    }
}
