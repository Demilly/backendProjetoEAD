package br.com.ead.service.impl;

import br.com.ead.controller.request.NotaRequest;
import br.com.ead.controller.response.ensino.modulo.NotaResponse;
import br.com.ead.model.entity.ensino.modulo.Modulo;
import br.com.ead.model.entity.ensino.modulo.Nota;
import br.com.ead.model.mapper.NotaMapper;
import br.com.ead.repository.ModuloRepository;
import br.com.ead.repository.NotaRepository;
import br.com.ead.service.NotaService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class NotaServiceImpl implements NotaService {

    private final NotaRepository notaRepository;
    private final ModuloRepository moduloRepository;
    private final NotaMapper notaMapper;

    @Transactional
    @Override
    public NotaResponse salvarNota(NotaRequest notaRequest) {
        Modulo modulo = moduloRepository.findById(notaRequest.getIdModulo())
                .orElseThrow(() -> new IllegalArgumentException("Módulo não encontrado"));

        Nota nota = notaMapper.toNota(notaRequest);
        nota.setModulo(modulo);

        Nota notaSalva = notaRepository.save(nota);
        return notaMapper.toNotaResponse(notaSalva);
    }

    @Override
    public List<NotaResponse> listarNotasPorAluno(Long usuarioId) {
        List<Nota> notas = notaRepository.findNotasByUsuarioId(usuarioId);
        return notas.stream()
                .map(notaMapper::toNotaResponse)
                .toList();
    }
}
