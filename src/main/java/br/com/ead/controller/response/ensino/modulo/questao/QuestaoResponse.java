package br.com.ead.controller.response.ensino.modulo.questao;

import br.com.ead.model.entity.ensino.modulo.Resposta;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class QuestaoResponse {

    private String uuid;
    private String pergunta;
    private String descricao;
    private Integer pontuacao;
    private String explicacao;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private Set<Resposta> respostas;
}
