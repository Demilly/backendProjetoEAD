package br.com.ead.controller.request.ensino.curso;

import lombok.Data;

@Data
public class CursoRequest {
    private String nome;
    private String descricao;
    private String urlBanner;
    private String cargaHoraria;
    private Long idInstituicao;
}
