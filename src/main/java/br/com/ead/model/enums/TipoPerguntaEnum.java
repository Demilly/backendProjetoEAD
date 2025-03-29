package br.com.ead.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipo de perguntas disponíveis no sistema.")
public enum TipoPerguntaEnum {

    @Schema(description = "MULTIPLA_ESCOLHA")
    MULTIPLA_ESCOLHA,

    @Schema(description = "UNICA_ESCOLHA")
    UNICA_ESCOLHA,
}
