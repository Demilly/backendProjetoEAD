package br.com.ead.service.impl;

import br.com.ead.controller.request.ensino.curso.CursoRequest;
import br.com.ead.controller.request.ensino.curso.UpdateRequest;
import br.com.ead.controller.request.ensino.modulo.ModuloRequest;
import br.com.ead.controller.request.QuestaoRequest;
import br.com.ead.controller.request.VideoAulaRequest;
import br.com.ead.controller.response.ensino.curso.CursoResponse;
import br.com.ead.model.entity.ensino.Curso;
import br.com.ead.model.entity.ensino.aula.Aula;
import br.com.ead.model.entity.ensino.aula.VideoAula;
import br.com.ead.model.entity.ensino.modulo.Modulo;
import br.com.ead.model.entity.ensino.modulo.Questao;
import br.com.ead.model.entity.instituicao.Instituicao;
import br.com.ead.model.mapper.*;
import br.com.ead.repository.CursoRepository;
import br.com.ead.repository.InstituicaoRepository;
import br.com.ead.service.CursoService;
import br.com.ead.service.exception.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CursoServiceImpl implements CursoService {

    private final InstituicaoRepository instituicaoRepository;
    private final CursoRepository cursoRepository;
    private final CursoMapper cursoMapper;
    private final ModuloMapper moduloMapper;
    private final AulaMapper aulaMapper;
    private final VideoAulaMapper videoAulaMapper;
    private final QuestaoMapper questaoMapper;

    @Override
    public List<CursoResponse> listarCursos() {
        var cursos = cursoRepository.findAll();
        return cursos.stream().map(cursoMapper::toCursoResponse).toList();
    }

    @Transactional
    @Override
    public Page<CursoResponse> listarCursosPaginada(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Curso> cursosPaginados = cursoRepository.findAll(pageable);
        return cursosPaginados.map(cursoMapper::toCursoResponse);
    }

    @Transactional
    @Override
    public CursoResponse cadastrarCurso(CursoRequest cursoRequest) {
        var cursoEntity = cursoMapper.toCurso(cursoRequest);

        Instituicao instituicao = buscarInstituicao(cursoRequest.getInstituicao());
        associarInstituicaoAoCurso(cursoEntity, instituicao);
        cursoEntity.setAtivo(cursoRequest.getAtivo());

        var cursoSalvo = cursoRepository.save(cursoEntity);
        return cursoMapper.toCursoResponse(cursoSalvo);
    }

    @Transactional
    @Override
    public CursoResponse buscarCursoPorId(Long id) {
        var curso = cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado com ID: " + id));
        return cursoMapper.toCursoResponse(curso);
    }

    @Override
    @Transactional
    public void deletarCurso(String uuid) {
        Curso curso = cursoRepository.findByUuid(uuid)
                .orElseThrow(() -> new BusinessException("Curso não localizado para o ID informado.", uuid));
        cursoRepository.delete(curso);
    }

    @Transactional
    @Override
    public CursoResponse atualizarCurso(String uuid, UpdateRequest updateRequest) {
        Curso cursoExistente = cursoRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado com ID: " + uuid));

        cursoExistente.setNome(updateRequest.getNome());
        cursoExistente.setDescricao(updateRequest.getDescricao());
        cursoExistente.setAtivo(updateRequest.getAtivo());
        cursoExistente.setUrlBanner(updateRequest.getUrlBanner());
        cursoExistente.setCargaHoraria(updateRequest.getCargaHoraria());

        Curso cursoAtualizado = cursoRepository.save(cursoExistente);
        return cursoMapper.toCursoResponse(cursoAtualizado);
    }

    private Instituicao buscarInstituicao(String cpfOuCnpj) {
        return instituicaoRepository.findByCpfOuCnpj(cpfOuCnpj)
                .orElseThrow(() -> new BusinessException("Instituição não localizada para o código informado.", cpfOuCnpj));
    }

    private void associarInstituicaoAoCurso(Curso curso, Instituicao instituicao) {
        curso.setInstituicao(instituicao);
        instituicao.addCurso(curso);
    }

    private Modulo criarModuloComAulas(ModuloRequest moduloRequest, Curso cursoEntity) {
        Modulo modulo = moduloMapper.toModulo(moduloRequest);
        modulo.setCurso(cursoEntity);
        return modulo;
    }

    private void adicionarQuestoesAula(QuestaoRequest questaoAulaRequest, Aula aula) {
        Questao questao = questaoMapper.toQuestao(questaoAulaRequest);
        questao.setAula(aula);
        aula.addQuestoes(questao);
    }

    private void adicionarVideoAula(VideoAulaRequest videoAulaRequest, Aula aula) {
        VideoAula videoAula = videoAulaMapper.toVideoAula(videoAulaRequest);
        videoAula.setAula(aula);
        aula.addVideos(videoAula);
    }
}
