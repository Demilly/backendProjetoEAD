package br.com.ead.model.entity.ensino.modulo;

import br.com.ead.model.entity.ensino.aula.Aula;
import br.com.ead.model.entity.ensino.Curso;
import br.com.ead.model.entity.instituicao.Instituicao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "modulo")
public class Modulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_modulo")
    private Long idModulo;

    @Column(name = "titulo_modulo")
    private String tituloModulo;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "ordem_modulo")
    private Integer ordemModulo;

    @Column(name = "data_criacao")
    @UpdateTimestamp
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    @CreationTimestamp
    private LocalDateTime dataAtualizacao;

    @OneToMany(mappedBy = "modulo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Aula> aulas = new ArrayList<>();

    @OneToMany(mappedBy = "modulo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Nota> notas = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    public void addAulas(Aula aula) {
        aula.setModulo(this);
        this.aulas.add(aula);
    }

    public void addNota(Nota nota) {
        nota.setModulo(this);
        this.notas.add(nota);
    }
}
