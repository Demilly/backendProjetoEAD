package br.com.ead.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipos de Usuários disponíveis no sistema")
public enum TipoUsuarioEnum {
    @Schema(description = "Administrador do sistema")
    ADMINISTRADOR,

    @Schema(description = "DIRETOR ")
    DIRETOR,

    @Schema(description = "SUPORTE ")
    SUPORTE,

    @Schema(description = "Professor")
    PROFESSOR,

    @Schema(description = "Aluno")
    ALUNO;
}
