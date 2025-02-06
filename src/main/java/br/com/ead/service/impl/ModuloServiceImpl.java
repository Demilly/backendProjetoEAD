package br.com.ead.service.impl;

import br.com.ead.controller.request.ensino.modulo.ModuloRequest;
import br.com.ead.controller.request.ensino.modulo.UpdateModuloRequest;
import br.com.ead.controller.response.ensino.modulo.ModuloResponse;
import br.com.ead.model.entity.ensino.modulo.Modulo;
import br.com.ead.model.mapper.ModuloMapper;
import br.com.ead.repository.CursoRepository;
import br.com.ead.repository.ModuloRepository;
import br.com.ead.service.ModuloService;
import br.com.ead.service.exception.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ModuloServiceImpl implements ModuloService {

    private final ModuloMapper moduloMapper;
    private final ModuloRepository moduloRepository;
    private final CursoRepository cursoRepository;

    @Override
    public List<ModuloResponse> listaModuloPorCurso(String uuidCurso) {
        return moduloRepository.findByCursoUuid(uuidCurso)
                .stream()
                .map(moduloMapper::toModuloResponse)
                .toList();
    }

    @Transactional
    @Override
    public ModuloResponse cadastrarModulo(ModuloRequest moduloRequest) {
        var modulo = moduloMapper.toModulo(moduloRequest);
        var curso = cursoRepository.findByUuid(moduloRequest.getUuidCurso())
                .orElseThrow(() -> new BusinessException("Curso não localizado para o código informado.", modulo.getCurso().getUuid()));

        modulo.setCurso(curso);
        var moduloSalvo = moduloRepository.save(modulo);
        return moduloMapper.toModuloResponse(moduloSalvo);
    }

    @Override
    public ModuloResponse atualizarModulo(String uuid, UpdateModuloRequest updateModuloRequest) {
        Modulo moduloExistente = moduloRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Modulo não encontrado com ID: " + uuid));

        moduloExistente.setTituloModulo(updateModuloRequest.getTituloModulo());
        moduloExistente.setDescricao(updateModuloRequest.getDescricao());
        moduloExistente.setOrdemModulo(updateModuloRequest.getOrdemModulo());

        Modulo moduloAtualizado = moduloRepository.save(moduloExistente);
        return moduloMapper.toModuloResponse(moduloAtualizado);
    }

    @Override
    public void deletarCurso(String uuid) {
        Modulo modulo = moduloRepository.findByUuid(uuid)
                .orElseThrow(() -> new BusinessException("Modulo não localizado para o ID informado.", uuid));
        moduloRepository.delete(modulo);
    }
}
