package br.com.ead.service.impl;

import br.com.ead.controller.request.AulaRequest;
import br.com.ead.controller.request.CursoRequest;
import br.com.ead.controller.request.ModuloRequest;
import br.com.ead.controller.request.VideoAulaRequest;
import br.com.ead.model.entity.ensino.Curso;
import br.com.ead.model.entity.ensino.aula.Aula;
import br.com.ead.model.entity.ensino.aula.VideoAula;
import br.com.ead.model.entity.ensino.modulo.Modulo;
import br.com.ead.model.entity.instituicao.Instituicao;
import br.com.ead.repository.CursoRepository;
import br.com.ead.repository.InstituicaoRepository;
import br.com.ead.repository.ModuloRepository;
import br.com.ead.service.CursoService;
import br.com.ead.service.exception.BusinessException;
import br.com.ead.service.mapper.AulaMapper;
import br.com.ead.service.mapper.CursoMapper;
import br.com.ead.service.mapper.ModuloMapper;
import br.com.ead.service.mapper.VideoAulaMapper;
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

    @Transactional
    @Override
    public Curso cadastrarCursoComModulos(CursoRequest cursoRequest) {
        var cursoEntity = cursoMapper.toCurso(cursoRequest);

        Instituicao instituicao = buscarInstituicao(cursoRequest.getIdInstituicao());
        associarInstituicaoAoCurso(cursoEntity, instituicao);

        cursoRequest.getModulos()
                .stream()
                .map(moduloRequest -> criarModuloComAulas(moduloRequest, cursoEntity))
                .forEach(cursoEntity::addModulos);

        cursoEntity.setIsAtivo(true);
        return cursoRepository.save(cursoEntity);
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

        moduloRequest.getAulas()
                .forEach(aulaRequest -> criarAulaComVideos(aulaRequest, modulo));

        return modulo;
    }

    private void criarAulaComVideos(AulaRequest aulaRequest, Modulo modulo) {
        Aula aula = aulaMapper.toAula(aulaRequest);
        aula.setModulo(modulo);
        modulo.addAulas(aula);

        aulaRequest.getVideos()
                .forEach(videoAulaRequest -> adicionarVideoAula(videoAulaRequest, aula));
    }

    private void adicionarVideoAula(VideoAulaRequest videoAulaRequest, Aula aula) {
        VideoAula videoAula = videoAulaMapper.toVideoAula(videoAulaRequest);
        videoAula.setAula(aula);
        aula.addVideos(videoAula);
    }
}
