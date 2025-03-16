package br.com.ead.service;

import br.com.ead.controller.request.ensino.modulo.ModuloRequest;
import br.com.ead.controller.request.ensino.modulo.UpdateModuloRequest;
import br.com.ead.controller.response.ensino.modulo.ModuloResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ModuloService {

    List<ModuloResponse> listaModuloPorCurso(String uuidCurso);

    Page<ModuloResponse> listarModuloPaginada(int page, int size);

   ModuloResponse cadastrarModulo(ModuloRequest moduloRequest, MultipartFile arquivo);

    ModuloResponse atualizarModulo(String uuid, UpdateModuloRequest updateModuloRequest);

    void deletarCurso(String uuid);
}
