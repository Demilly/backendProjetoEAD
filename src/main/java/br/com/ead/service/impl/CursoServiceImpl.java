package br.com.ead.service.impl;

import br.com.ead.controller.request.CursoRequest;
import br.com.ead.controller.request.ModuloRequest;
import br.com.ead.controller.request.QuestaoRequest;
import br.com.ead.controller.request.VideoAulaRequest;
import br.com.ead.controller.response.ensino.CursoResponse;
import br.com.ead.model.entity.ensino.Curso;
import br.com.ead.model.entity.ensino.aula.Aula;
import br.com.ead.model.entity.ensino.aula.VideoAula;
import br.com.ead.model.entity.ensino.modulo.Modulo;
import br.com.ead.model.entity.ensino.modulo.Questao;
import br.com.ead.model.entity.instituicao.Instituicao;
import br.com.ead.repository.CursoRepository;
import br.com.ead.repository.InstituicaoRepository;
import br.com.ead.service.CursoService;
import br.com.ead.service.exception.BusinessException;
import br.com.ead.service.mapper.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    @Transactional
    @Override
    public CursoResponse cadastrarCursoComModulos(CursoRequest cursoRequest) {
        var cursoEntity = cursoMapper.toCurso(cursoRequest);

        Instituicao instituicao = buscarInstituicao(cursoRequest.getIdInstituicao());
        associarInstituicaoAoCurso(cursoEntity, instituicao);

        cursoRequest.getModulos()
                .stream()
                .map(moduloRequest -> criarModuloComAulas(moduloRequest, cursoEntity))
                .forEach(cursoEntity::addModulos);

        cursoEntity.setIsAtivo(true);

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
    public void deletarCurso(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Curso não localizado para o ID informado."));
        cursoRepository.delete(curso);
    }

    @Transactional
    @Override
    public CursoResponse atualizarCurso(Long id, CursoRequest cursoRequest) {
        Curso cursoExistente = cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado com ID: " + id));

        cursoMapper.updateCursoFromRequest(cursoRequest, cursoExistente);

        Instituicao instituicao = buscarInstituicao(cursoRequest.getIdInstituicao());
        associarInstituicaoAoCurso(cursoExistente, instituicao);

        cursoExistente.getModulos().clear();
        cursoRequest.getModulos()
                .stream()
                .map(moduloRequest -> criarModuloComAulas(moduloRequest, cursoExistente))
                .forEach(cursoExistente::addModulos);

        Curso cursoAtualizado = cursoRepository.save(cursoExistente);
        return cursoMapper.toCursoResponse(cursoAtualizado);
    }

    private Instituicao buscarInstituicao(Long idInstituicao) {
        return instituicaoRepository.findById(idInstituicao)
                .orElseThrow(() -> new BusinessException("Instituição não localizada para o código informado."));
    }

    private void associarInstituicaoAoCurso(Curso curso, Instituicao instituicao) {
        curso.setInstituicao(instituicao);
        instituicao.addCurso(curso);
    }

    private Modulo criarModuloComAulas(ModuloRequest moduloRequest, Curso cursoEntity) {
        Modulo modulo = moduloMapper.toModulo(moduloRequest);
        modulo.setCurso(cursoEntity);

        moduloRequest.getAulas().forEach(aulaRequest -> {
            Aula aula = aulaMapper.toAula(aulaRequest);
            aula.setModulo(modulo);
            aulaRequest.getVideos().forEach(videoAulaRequest -> adicionarVideoAula(videoAulaRequest, aula));
            aulaRequest.getQuestoes().forEach(questaoRequest -> adicionarQuestoesAula(questaoRequest, aula));
            modulo.addAulas(aula);
        });

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
