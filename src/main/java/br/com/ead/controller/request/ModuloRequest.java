package br.com.ead.controller.request;

import lombok.Data;

import java.util.List;

@Data
public class ModuloRequest {

    private String tituloModulo;
    private String descricao;
    private Integer ordemModulo;
    private List<AulaRequest> aulas;
}
