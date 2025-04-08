package br.com.ead.service.impl;

import br.com.ead.controller.request.ensino.aula.AulaRequest;
import br.com.ead.controller.response.UploadResponse;
import br.com.ead.controller.response.ensino.aula.AulaResponse;
import br.com.ead.model.entity.ensino.Curso;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<AulaResponse> listarAulasPaginada(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AulaEntity> aulasPaginadas = aulaRepository.findAll(pageable);
        return aulasPaginadas.map(aulaMapper::toAulaResponse);
    }

    @Override
    public Page<AulaResponse> listarAulasPaginadaPorModulo(String uuidModulo, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AulaEntity> aulasPaginadas = aulaRepository.findAllByModuloUuid(uuidModulo, pageable);
        return aulasPaginadas.map(aulaMapper::toAulaResponse);
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
    public AulaResponse atualizarAula(String uuid, AulaRequest aulaRequest, List<MultipartFile> arquivos) {

        return aulaRepository.findByUuid(uuid)
                .map(aulaExistente -> {
                    aulaExistente.setTitulo(aulaRequest.getTitulo());
                    aulaExistente.setDescricao(aulaRequest.getDescricao());
                    aulaExistente.setDuracaoMinutos(aulaRequest.getDuracaoMinutos());
                    aulaExistente.setOrdemAula(aulaRequest.getOrdemAula());

                    // Atualizar módulo se mudou
                    if (!aulaExistente.getModulo().getUuid().equals(aulaRequest.getUuidModulo())) {
                        var novoModulo = moduloRepository.findByUuid(aulaRequest.getUuidModulo())
                                .orElseThrow(() -> new BusinessException("Módulo não encontrado"));
                        aulaExistente.setModulo(novoModulo);
                    }
                    // Fazer upload dos novos arquivos, se houver
                    if (!CollectionUtils.isEmpty(arquivos)) {
                        uploadS3(arquivos, aulaExistente);
                    }

                    // Salvar alterações
                    var aulaAtualizada = aulaRepository.save(aulaExistente);

                    return aulaMapper.toAulaResponse(aulaAtualizada);
                })
                .orElseThrow(() -> new RuntimeException("Aula não encontrada"));
    }


    @Override
    public void excluirAula(String uuid) {
        AulaEntity aula = aulaRepository.findByUuid(uuid)
                .orElseThrow(() -> new BusinessException("Aula não localizada para o uuid informado.", uuid));

        // Excluir vídeos associados do Amazon S3
        if (aula.getVideos() != null && !aula.getVideos().isEmpty()) {
            for (VideoAula video : aula.getVideos()) {
                String videoUrl = video.getUrl();
                if (videoUrl != null && !videoUrl.isEmpty()) {
                    armazenamentoS3Service.deletarArquivo(videoUrl, "AULA/VIDEOS");
                }
            }
        }
        videoAulaRepository.deleteAll(aula.getVideos());

        aulaRepository.delete(aula);
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
