package br.com.ead.controller.response.ensino.modulo;

import br.com.ead.controller.response.ensino.aula.AulaResponse;
import br.com.ead.model.entity.ensino.Curso;
import br.com.ead.model.entity.ensino.modulo.LeituraComplementar;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ModuloResponse {

    private String tituloModulo;
    private String descricao;
    private Integer ordemModulo;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private List<AulaResponse> aulas;
    private Curso curso;
    private List<String> urlArquivo;
    private String uuid;
    private List<LeituraComplementar> leiturasComplementares;
}