package br.com.ead.model.entity.ensino.modulo;


import br.com.ead.model.entity.ensino.aula.Aula;
import br.com.ead.model.entity.ensino.aula.Comentario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "questao")
public class Questao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_questao")
    private Long idQuestao;

    @Column(name = "titulo_questao")
    private String tituloQuestao;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "resposta_correta")
    private String respostaCorreta;

    @Column(name = "pontuacao")
    private Integer pontuacao;

    @Column(name = "data_criacao")
    @CreationTimestamp
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "aula_id")
    private Aula aula;

    @OneToMany(mappedBy = "questao", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comentario> comentarios = new HashSet<>();
}
