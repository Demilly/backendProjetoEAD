package br.com.ead.controller.request.ensino.curso;

import br.com.ead.model.enums.CategoriaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateRequest {
    private String nome;
    private String descricao;
    private String urlBanner;
    private String cargaHoraria;
    private Boolean ativo;
    @Schema(description = "Categoria", example = "APRENDIZAGEM", allowableValues = "BIBLIOTECA_PROFESSOR, BIBLIOTECA_ALUNO, APRENDIZAGEM")
    private CategoriaEnum categoria;
    private String idInstituicao;
}
