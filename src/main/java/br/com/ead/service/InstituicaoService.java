package br.com.ead.service;

import br.com.ead.controller.request.instituicao.InstituicaoRequest;
import br.com.ead.controller.response.instituicao.InstituicaoResponse;
import br.com.ead.model.entity.instituicao.Instituicao;

import java.util.List;

public interface InstituicaoService {

    List<InstituicaoResponse> listarTodasInstituicoes();

    InstituicaoResponse salvarInstituicao(Instituicao instituicao);

    InstituicaoResponse atualizarInstituicao(Long id, InstituicaoRequest instituicaoRequest);
}
