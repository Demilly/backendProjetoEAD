package br.com.ead.model.entity.ensino.modulo;

import br.com.ead.model.entity.ensino.Curso;
import br.com.ead.model.entity.ensino.aula.AulaEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "modulo")
public class Modulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_modulo")
    private Long idModulo;

    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    private String uuid = UUID.randomUUID().toString();

    @Column(name = "titulo_modulo")
    private String tituloModulo;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "ordem_modulo")
    private Integer ordemModulo;

    @Column(name = "data_criacao")
    @CreationTimestamp
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;

    @OneToMany(mappedBy = "modulo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AulaEntity> aulas = new ArrayList<>();

    @OneToMany(mappedBy = "modulo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Nota> notas = new ArrayList<>();

    @OneToMany(mappedBy = "modulo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LeituraComplementar> leiturasComplementares = new ArrayList<>();

    @OneToMany(mappedBy = "modulo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Questao> questoes = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "modulo_arquivos", joinColumns = @JoinColumn(name = "modulo_id"))
    @Column(name = "url_arquivo")
    private List<String> urlArquivo = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    public void addQuestoes(Questao questao) {
        questao.setModulo(this);
        this.questoes.add(questao);
    }

    public void addAulas(AulaEntity aulaEntity) {
        aulaEntity.setModulo(this);
        this.aulas.add(aulaEntity);
    }

    public void addNota(Nota nota) {
        nota.setModulo(this);
        this.notas.add(nota);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("idModulo", idModulo)
                .append("uuid", uuid)
                .append("tituloModulo", tituloModulo)
                .append("descricao", descricao)
                .append("ordemModulo", ordemModulo)
                .append("dataCriacao", dataCriacao)
                .append("dataAtualizacao", dataAtualizacao)
                .toString();
    }
}
