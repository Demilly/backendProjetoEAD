package br.com.ead.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Categoria disponíveis dos cursos sistema")
public enum CategoriaEnum {

    @Schema(description = "BIBLIOTECA_PROFESSOR")
    BIBLIOTECA_PROFESSOR,

    @Schema(description = "BIBLIOTECA_ALUNO")
    BIBLIOTECA_ALUNO,

    @Schema(description = "APRENDIZAGEM")
    APRENDIZAGEM;
}
