package br.com.ead.service;

import br.com.ead.controller.request.ensino.modulo.ModuloRequest;
import br.com.ead.controller.request.ensino.modulo.UpdateModuloRequest;
import br.com.ead.controller.response.ensino.modulo.ModuloResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ModuloService {

    Page<ModuloResponse> listarModuloPaginadaPorCurso(String uuidCurso, int page, int size);

    List<ModuloResponse> listarModuloPorUuidCurso(String uuidCurso);

    Page<ModuloResponse> listarModuloPaginada(int page, int size);

    ModuloResponse cadastrarModulo(ModuloRequest moduloRequest, List<MultipartFile> arquivos);

    ModuloResponse atualizarModulo(String uuid, UpdateModuloRequest updateModuloRequest, List<MultipartFile> arquivos);

    void deletarModulo(String uuid);
}
