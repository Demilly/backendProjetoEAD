package br.com.ead.service.impl;

import br.com.ead.controller.request.ensino.modulo.ModuloRequest;
import br.com.ead.controller.request.ensino.modulo.UpdateModuloRequest;
import br.com.ead.controller.response.ensino.modulo.ModuloResponse;
import br.com.ead.model.entity.ensino.modulo.LeituraComplementar;
import br.com.ead.model.entity.ensino.modulo.Modulo;
import br.com.ead.model.mapper.ModuloMapper;
import br.com.ead.repository.CursoRepository;
import br.com.ead.repository.ModuloRepository;
import br.com.ead.service.ArmazenamentoS3Service;
import br.com.ead.service.ModuloService;
import br.com.ead.service.exception.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ModuloServiceImpl implements ModuloService {

    private final ModuloMapper moduloMapper;
    private final ModuloRepository moduloRepository;
    private final CursoRepository cursoRepository;
    private ArmazenamentoS3Service armazenamentoS3Service;

    @Override
    public Page<ModuloResponse> listarModuloPaginadaPorCurso(String uuidCurso, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Modulo> modulosPaginados = moduloRepository.findByCursoUuid(uuidCurso, pageable);
        return modulosPaginados.map(moduloMapper::toModuloResponse);
    }

    @Override
    public List<ModuloResponse> listarModuloPorUuidCurso(String uuidCurso) {
        List<Modulo> modulos = moduloRepository.findByCursoUuid(uuidCurso);
        return modulos.stream().map(moduloMapper::toModuloResponse).toList();
    }

    @Override
    public Page<ModuloResponse> listarModuloPaginada(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Modulo> modulosPaginados = moduloRepository.findAll(pageable);
        return modulosPaginados.map(moduloMapper::toModuloResponse);
    }


    @Transactional
    @Override
    public ModuloResponse cadastrarModulo(ModuloRequest moduloRequest, MultipartFile arquivo) {
        var moduloEntity = moduloMapper.toModulo(moduloRequest);
        var curso = cursoRepository.findByUuid(moduloRequest.getUuidCurso())
                .orElseThrow(() -> new BusinessException("Curso não localizado para o código informado.", moduloEntity.getCurso().getUuid()));

        if (arquivo != null && !arquivo.isEmpty()) {
            uploadS3(arquivo, moduloEntity);
        }

        moduloEntity.setCurso(curso);

        // Verifica se há leituras complementares no request e adiciona ao módulo
        if (moduloRequest.getLeiturasComplementares() != null && !moduloRequest.getLeiturasComplementares().isEmpty()) {
            List<LeituraComplementar> leiturasComplementares = moduloRequest.getLeiturasComplementares().stream()
                    .map(leituraRequest -> {
                        LeituraComplementar leitura = new LeituraComplementar();
                        leitura.setTitulo(leituraRequest.getTitulo());
                        leitura.setTxtUrl(leituraRequest.getTxtUrl());
                        leitura.setModulo(moduloEntity); // Relaciona com o módulo
                        return leitura;
                    })
                    .collect(Collectors.toList());
            moduloEntity.setLeiturasComplementares(leiturasComplementares);
        }

        var moduloSalvo = moduloRepository.save(moduloEntity);
        return moduloMapper.toModuloResponse(moduloSalvo);
    }

    @Override
    public ModuloResponse atualizarModulo(String uuid, UpdateModuloRequest updateModuloRequest, MultipartFile arquivo) {
        Modulo moduloExistente = moduloRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Modulo não encontrado com ID: " + uuid));

        moduloExistente.setTituloModulo(updateModuloRequest.getTituloModulo());
        moduloExistente.setDescricao(updateModuloRequest.getDescricao());
        moduloExistente.setOrdemModulo(updateModuloRequest.getOrdemModulo());

        if (arquivo != null && !arquivo.isEmpty()) {
            uploadS3(arquivo, moduloExistente);
        }

        // Atualiza as leituras complementares, garantindo que antigas sejam removidas e novas adicionadas
        if (updateModuloRequest.getLeiturasComplementares() != null) {
            // Remove todas as leituras antigas
            moduloExistente.getLeiturasComplementares().clear();

            // Adiciona as novas leituras complementares
            List<LeituraComplementar> novasLeituras = updateModuloRequest.getLeiturasComplementares().stream()
                    .map(leituraData -> {
                        LeituraComplementar leitura = new LeituraComplementar();
                        leitura.setTitulo(leituraData.getTitulo()); // Obtém do mapa
                        leitura.setTxtUrl(leituraData.getTxtUrl()); // Obtém do mapa
                        leitura.setModulo(moduloExistente); // Relaciona com o módulo
                        return leitura;
                    })
                    .collect(Collectors.toList());

            moduloExistente.getLeiturasComplementares().addAll(novasLeituras);
        }

        Modulo moduloAtualizado = moduloRepository.save(moduloExistente);
        return moduloMapper.toModuloResponse(moduloAtualizado);
    }

    @Override
    public void deletarCurso(String uuid) {
        Modulo modulo = moduloRepository.findByUuid(uuid)
                .orElseThrow(() -> new BusinessException("Modulo não localizado para o ID informado.", uuid));
        moduloRepository.delete(modulo);
    }

    private void uploadS3(MultipartFile arquivo, Modulo moduloEntity) {

        // Deleta o arquivo do S3, se tiver uma URL válida
        if (moduloEntity.getUrlArquivo() != null && !moduloEntity.getUrlArquivo().isBlank() && !moduloEntity.getUrlArquivo().isEmpty()) {
            armazenamentoS3Service.deletarArquivo(moduloEntity.getUrlArquivo(), " modulo");
        }

        var responseS3 = armazenamentoS3Service.uploadDocumento(arquivo, "modulo");

        if(responseS3 != null && !responseS3.getCaminhoArquivo().isEmpty()) {
            moduloEntity.setUrlArquivo(responseS3.getCaminhoArquivo());
        }
    }
}
