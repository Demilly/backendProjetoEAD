package br.com.ead.model.entity.ensino.modulo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "resposta")
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resposta")
    private Long idResposta;

    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    private String uuid = UUID.randomUUID().toString();

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "opcao", nullable = false)
    private String opcao;

    @Column(name = "correta", nullable = false)
    private boolean correta;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "questao_id", nullable = false)
    private Questao questao;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("idResposta", idResposta)
                .append("uuid", uuid)
                .append("descricao", descricao)
                .append("opcao", opcao)
                .append("correta", correta)
                .toString();
    }
}