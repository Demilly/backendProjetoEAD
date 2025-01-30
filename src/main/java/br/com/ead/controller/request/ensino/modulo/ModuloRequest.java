package br.com.ead.controller.request.ensino.modulo;

import br.com.ead.controller.request.ensino.aula.AulaRequest;
import lombok.Data;

import java.util.List;

@Data
public class ModuloRequest {

    private String tituloModulo;
    private String descricao;
    private Integer ordemModulo;
    private List<AulaRequest> aulas;
}
