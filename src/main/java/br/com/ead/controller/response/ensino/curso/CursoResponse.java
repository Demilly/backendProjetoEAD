package br.com.ead.controller.response.ensino.curso;

import br.com.ead.controller.response.ensino.modulo.ModuloResponse;
import br.com.ead.controller.response.instituicao.InstituicaoResponse;
import br.com.ead.model.enums.CategoriaEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CursoResponse {

    private String nome;
    private String descricao;
    private Boolean ativo;
    private String urlBanner;
    private String cargaHoraria;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private InstituicaoResponse instituicao;
    private List<ModuloResponse> modulos;
    private String uuid;
    private CategoriaEnum categoria;
}
