package br.com.ead.model.entity.ensino.modulo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "resposta")
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resposta")
    private Long idResposta;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "correta", nullable = false)
    private boolean correta;

    @ManyToOne
    @JoinColumn(name = "questao_id", nullable = false)
    private Questao questao;
}