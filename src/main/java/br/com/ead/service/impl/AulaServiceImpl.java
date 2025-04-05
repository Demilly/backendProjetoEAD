package br.com.ead.service.impl;

import br.com.ead.controller.request.ensino.aula.AulaRequest;
import br.com.ead.controller.response.UploadResponse;
import br.com.ead.controller.response.ensino.aula.AulaResponse;
import br.com.ead.model.entity.ensino.aula.AulaEntity;
import br.com.ead.model.entity.ensino.aula.VideoAula;
import br.com.ead.model.mapper.AulaMapper;
import br.com.ead.model.mapper.VideoAulaRepository;
import br.com.ead.repository.AulaRepository;
import br.com.ead.repository.ModuloRepository;
import br.com.ead.service.ArmazenamentoS3Service;
import br.com.ead.service.AulaService;
import br.com.ead.service.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.mapstruct.ap.shaded.freemarker.core.BugException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AulaServiceImpl implements AulaService {

    private final AulaRepository aulaRepository;
    private final ModuloRepository moduloRepository;
    private final AulaMapper aulaMapper;
    private final VideoAulaRepository videoAulaRepository;
    private final ArmazenamentoS3Service armazenamentoS3Service;

    @Override
    public AulaResponse criarAula(AulaRequest aulaRequest, List<MultipartFile> arquivos) {
        var aula = aulaMapper.toAula(aulaRequest);
        var modulo = moduloRepository.findByUuid(aulaRequest.getUuidModulo())
                .orElseThrow(() -> new RuntimeException("Módulo não encontrado"));

        aula.setModulo(modulo);
        var aulaEntity = aulaRepository.save(aula);

        if (!CollectionUtils.isEmpty(arquivos)) {
            uploadS3(arquivos, aulaEntity);
        }

        return aulaMapper.toAulaResponse(aulaEntity);
    }

    @Override
    public AulaResponse getAula(String uuid) {
        var aula = aulaRepository.findByUuid(uuid)
                .orElseThrow(() -> new BusinessException("Aula não encontrada"));
        return aulaMapper.toAulaResponse(aula);
    }

    @Override
    public List<AulaResponse> listarAulas() {
        return List.of();
    }

    @Override
    public void atualizarAula(Long id, AulaRequest aulaRequest) {

    }

    @Override
    public void excluirAula(Long id) {

    }

    private void uploadS3(List<MultipartFile> arquivos, AulaEntity aulaEntity) {
        if (aulaEntity.getVideos() == null) {
            aulaEntity.setVideos(new ArrayList<>());
        }

        for (MultipartFile arquivo : arquivos) {
            var responseS3 = armazenamentoS3Service.uploadDocumento(arquivo, "AULA/VIDEOS");

            if (responseS3 != null && !responseS3.getCaminhoArquivo().isEmpty()) {
                VideoAula videoAula = getVideoAula(aulaEntity, arquivo, responseS3);

                videoAulaRepository.save(videoAula);
                aulaEntity.getVideos().add(videoAula);
            }
        }
        aulaRepository.save(aulaEntity);
    }

    private static VideoAula getVideoAula(AulaEntity aulaEntity, MultipartFile arquivo, UploadResponse responseS3) {
        VideoAula videoAula = new VideoAula();
        videoAula.setUrl(responseS3.getCaminhoArquivo());
        videoAula.setDuracao(arquivo.getSize());
        videoAula.setDataUpload(responseS3.getDataHoraUpload());
        videoAula.setAula(aulaEntity);
        return videoAula;
    }
}
