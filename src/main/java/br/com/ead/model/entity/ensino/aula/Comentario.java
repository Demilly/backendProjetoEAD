package br.com.ead.model.entity.ensino.aula;

import br.com.ead.model.entity.ensino.modulo.Questao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comentario")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comentario")
    private Long idComentario;

    @Column(name = "texto", nullable = false)
    private String texto;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "aula_id", nullable = false)
    private Aula aula;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "questao_id", nullable = false)
    private Questao questao;
}
