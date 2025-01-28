package br.com.ead.service.impl;

import br.com.ead.controller.response.ensino.aula.ProgressaoAulaResponse;
import br.com.ead.model.entity.ensino.aula.ProgressaoAula;
import br.com.ead.model.mapper.ProgressaoAulaMapper;
import br.com.ead.repository.ProgressaoAulaRepository;
import br.com.ead.service.ProgressaoAulaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ProgressaoAulaServiceImpl implements ProgressaoAulaService {

    private final ProgressaoAulaRepository progressaoAulaRepository;
    private final ProgressaoAulaMapper progressaoAulaMapper;

    @Override
    public List<ProgressaoAulaResponse> listarProgressaoPorAluno(Long usuarioId) {
        List<ProgressaoAula> progressaoAulas = progressaoAulaRepository.findProgressaoAulasByUsuarioId(usuarioId);
        return progressaoAulas.stream()
                .map(progressaoAulaMapper::toProgressaoAulaResponse)
                .toList();
    }
}
