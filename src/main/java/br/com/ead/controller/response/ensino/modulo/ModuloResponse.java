package br.com.ead.controller.response.ensino.modulo;

import br.com.ead.controller.response.ensino.aula.AulaResponse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ModuloResponse {

    private String tituloModulo;
    private String descricao;
    private Integer ordemModulo;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private List<AulaResponse> aulas;
}