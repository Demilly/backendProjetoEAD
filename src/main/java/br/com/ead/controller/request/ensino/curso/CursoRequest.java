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
@Schema(description = "Dados do curso para cadastro")
public class CursoRequest {
    @Schema(description = "Nome do curso", example = "Java")
    private String nome;
    @Schema(description = "Descrição do curso", example = "Curso básico de Java")
    private String descricao;
    private String urlBanner;
    private String cargaHoraria;
    private Boolean ativo;
    @Schema(description = "Categoria", example = "APRENDIZAGEM", allowableValues = "BIBLIOTECA_PROFESSOR, BIBLIOTECA_ALUNO, APRENDIZAGEM")
    private CategoriaEnum categoria;
}
