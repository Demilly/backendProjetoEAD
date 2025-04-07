package br.com.ead.service;

import br.com.ead.controller.request.ensino.aula.AulaRequest;
import br.com.ead.controller.response.ensino.aula.AulaResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AulaService {


    AulaResponse criarAula(AulaRequest aulaRequest, List<MultipartFile> arquivos);

    Page<AulaResponse> listarAulasPaginada(int page, int size);

    Page<AulaResponse> listarAulasPaginadaPorModulo(String uuidModulo, int page, int size);

    AulaResponse getAula(String uuid);

    List<AulaResponse> listarAulas();

    void atualizarAula(Long id, AulaRequest aulaRequest);

    void excluirAula(Long id);
}
