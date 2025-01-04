package br.com.ead.controller.request;

import lombok.Data;

import java.util.List;

@Data
public class CursoRequest {
    private String nome;
    private String descricao;
    private String urlBanner;
    private String cargaHoraria;
    private Long idInstituicao;
    private List<ModuloRequest> modulos;
}
