package br.com.ead.controller.request.ensino.modulo;

import br.com.ead.model.entity.ensino.modulo.LeituraComplementar;
import br.com.ead.model.entity.ensino.modulo.Questao;
import lombok.Data;

import java.util.List;

@Data
public class ModuloRequest {

    private String tituloModulo;
    private String descricao;
    private Integer ordemModulo;
    private String uuidCurso;
    private List<LeituraComplementar> leiturasComplementares;
}
