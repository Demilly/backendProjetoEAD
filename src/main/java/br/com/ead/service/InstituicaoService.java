package br.com.ead.service;

import br.com.ead.controller.request.InstituicaoRequest;
import br.com.ead.controller.response.instituicao.InstituicaoResponse;
import br.com.ead.model.entity.instituicao.Instituicao;

public interface InstituicaoService {

    InstituicaoResponse salvarInstituicao(Instituicao instituicao);

    InstituicaoResponse atualizarInstituicao(Long id, InstituicaoRequest instituicaoRequest);
}
