package br.com.ead.controller.request.ensino.modulo;

import lombok.Data;

@Data
public class ModuloRequest {

    private String tituloModulo;
    private String descricao;
    private Integer ordemModulo;
    private String uuidCurso;
    private String urlArquivo;
}
