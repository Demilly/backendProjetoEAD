package br.com.ead.model.entity.ensino.aula;

import br.com.ead.model.entity.ensino.modulo.Modulo;
import br.com.ead.model.entity.ensino.modulo.Questao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "aula")
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aula")
    private Long idAula;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "duracao_minutos")
    private Integer duracaoMinutos;

    @Column(name = "ordem_aula")
    private Integer ordemAula;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "modulo_id")
    private Modulo modulo;

    @OneToMany(mappedBy = "aula", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VideoAula> videos = new ArrayList<>();

    @OneToMany(mappedBy = "aula", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios = new ArrayList<>();

    @OneToMany(mappedBy = "aula", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProgressaoAula> progressaoAulas = new ArrayList<>();

    @OneToMany(mappedBy = "aula", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Questao> questoes = new ArrayList<>();

    public void addQuestoes(Questao questao) {
        questao.setAula(this);
        this.questoes.add(questao);
    }

    public void addVideos(VideoAula videoAula) {
        videoAula.setAula(this);
        this.videos.add(videoAula);
    }
}
