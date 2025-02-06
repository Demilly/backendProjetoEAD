package br.com.ead.service;

import br.com.ead.controller.request.ensino.modulo.ModuloRequest;
import br.com.ead.controller.request.ensino.modulo.UpdateModuloRequest;
import br.com.ead.controller.response.ensino.modulo.ModuloResponse;

import java.util.List;

public interface ModuloService {

    List<ModuloResponse> listaModuloPorCurso(String uuidCurso);

    ModuloResponse cadastrarModulo(ModuloRequest moduloRequest);

    ModuloResponse atualizarModulo(String uuid, UpdateModuloRequest updateModuloRequest);

    void deletarCurso(String uuid);
}
