package br.com.ead.service;

import br.com.ead.controller.response.InstituicaoResponse;
import br.com.ead.model.entity.instituicao.Instituicao;

@FunctionalInterface
public interface InstituicaoService {

    InstituicaoResponse salvarInstituicao(Instituicao instituicao);
}
