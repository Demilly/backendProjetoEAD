package br.com.ead.service.impl;

import br.com.ead.controller.request.ensino.curso.CursoRequest;
import br.com.ead.controller.request.ensino.curso.UpdateRequest;
import br.com.ead.controller.response.ensino.curso.CursoResponse;
import br.com.ead.model.entity.ensino.Curso;
import br.com.ead.model.entity.instituicao.Instituicao;
import br.com.ead.model.entity.usuario.Usuario;
import br.com.ead.model.mapper.CursoMapper;
import br.com.ead.repository.CursoRepository;
import br.com.ead.repository.InstituicaoRepository;
import br.com.ead.repository.UsuarioRepository;
import br.com.ead.service.ArmazenamentoS3Service;
import br.com.ead.service.CursoService;
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


@AllArgsConstructor
@Service
public class CursoServiceImpl implements CursoService {

    private final InstituicaoRepository instituicaoRepository;
    private final CursoRepository cursoRepository;
    private final CursoMapper cursoMapper;
    private ArmazenamentoS3Service armazenamentoS3Service;
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<CursoResponse> listarCursosDoUsuario(String cpfOuCnpj) {
        Usuario usuario = usuarioRepository.findByCpfOuCnpj(cpfOuCnpj)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        var cursos = usuario.getCursos();

        return cursos.stream().map(cursoMapper::toCursoResponse).toList();
    }

    @Override
    public List<CursoResponse> listarTodos() {
        var cursos = cursoRepository.findAll();
        return cursos.stream().map(cursoMapper::toCursoResponse).toList();
    }

    @Override
    public List<CursoResponse> listarCursos(Long instituicaoId) {
        List<Curso> cursos;

        if (instituicaoId != null) {
            Instituicao instituicao = new Instituicao();
            instituicao.setIdInstituicao(instituicaoId);

            cursos = cursoRepository.findByInstituicoesContaining(instituicao);
        } else {
            cursos = cursoRepository.findAll();
        }

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
    public CursoResponse cadastrarCurso(CursoRequest cursoRequest, MultipartFile imagem) {
        var cursoEntity = cursoMapper.toCurso(cursoRequest);

        if (imagem != null && !imagem.isEmpty()) {
            uploadS3(imagem, cursoEntity);
        }

        cursoEntity.setAtivo(cursoRequest.getAtivo());

        var cursoSalvo = cursoRepository.save(cursoEntity);
        return cursoMapper.toCursoResponse(cursoSalvo);
    }

    private void uploadS3(MultipartFile imagem, Curso cursoEntity) {

        if (cursoEntity.getUrlBanner() != null && !cursoEntity.getUrlBanner().isBlank()) {
            armazenamentoS3Service.deletarArquivo(cursoEntity.getUrlBanner(), "curso");
        }

        var responseS3 = armazenamentoS3Service.uploadImagem(imagem, "curso");

        if (responseS3 != null && !responseS3.getCaminhoArquivo().isEmpty()) {
            cursoEntity.setUrlBanner(responseS3.getCaminhoArquivo());
        }
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

        String imagemUrl = curso.getUrlBanner();

        cursoRepository.delete(curso);

        // Deleta a imagem do S3, se tiver uma URL válida
        if (imagemUrl != null && !imagemUrl.isBlank()) {
            armazenamentoS3Service.deletarArquivo(imagemUrl, "curso");
        }
    }

    @Transactional
    @Override
    public CursoResponse atualizarCurso(String uuid, UpdateRequest updateRequest, MultipartFile imagem) {
        Curso cursoExistente = cursoRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado com ID: " + uuid));

        cursoExistente.setNome(updateRequest.getNome());
        cursoExistente.setDescricao(updateRequest.getDescricao());
        cursoExistente.setAtivo(updateRequest.getAtivo());
        cursoExistente.setUrlBanner(updateRequest.getUrlBanner());
        cursoExistente.setCategoria(updateRequest.getCategoria());
        cursoExistente.setCargaHoraria(updateRequest.getCargaHoraria());

        if (imagem != null && !imagem.isEmpty()) {
            uploadS3(imagem, cursoExistente);
        }

        Curso cursoAtualizado = cursoRepository.save(cursoExistente);
        return cursoMapper.toCursoResponse(cursoAtualizado);
    }

    private Instituicao buscarInstituicao(String cpfOuCnpj) {
        return instituicaoRepository.findByCpfOuCnpj(cpfOuCnpj)
                .orElseThrow(() -> new BusinessException("Instituição não localizada para o código informado.", cpfOuCnpj));
    }

}
