package br.com.ead.controller.request.ensino.curso;


import br.com.ead.model.enums.CategoriaEnum;
import br.com.ead.model.enums.TipoUsuarioEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CursoRequest {
    private String nome;
    private String descricao;
    private String urlBanner;
    private String cargaHoraria;
    private Boolean ativo;
    private String instituicao;
    @Schema(description = "Categoria", example = "APRENDIZAGEM", allowableValues = "BIBLIOTECA_PROFESSOR, BIBLIOTECA_ALUNO, APRENDIZAGEM")
    private CategoriaEnum categoria;
}
