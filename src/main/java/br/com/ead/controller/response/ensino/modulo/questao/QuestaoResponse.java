package br.com.ead.controller.response.ensino.modulo.questao;

import br.com.ead.controller.response.RespostaResponse;
import br.com.ead.model.entity.ensino.modulo.Modulo;
import br.com.ead.model.enums.TipoPerguntaEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class QuestaoResponse {

    private String uuid;
    private String pergunta;
    private TipoPerguntaEnum tipoPergunta;
    private String descricao;
    private Integer pontuacao;
    private String explicacao;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private Set<RespostaResponse> respostas;
    private String uuidCurso;
    private String uuidModulo;

}
