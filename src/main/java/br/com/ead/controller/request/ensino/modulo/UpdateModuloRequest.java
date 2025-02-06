package br.com.ead.controller.request.ensino.modulo;

import lombok.Data;

@Data
public class UpdateModuloRequest {

    private String tituloModulo;
    private String descricao;
    private Integer ordemModulo;
    private String uuidCurso;
}
