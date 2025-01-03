package br.com.ead.model.entity.ensino;

import br.com.ead.model.entity.ensino.modulo.Modulo;
import br.com.ead.model.entity.instituicao.Instituicao;
import br.com.ead.model.entity.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "curso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private Long idCurso;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "ativo")
    private Boolean isAtivo;

    @Column(name = "url_banner")
    private String urlBanner;

    @Column(name = "carga_horaria")
    private String cargaHoraria;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "instituicao_id", nullable = false)
    private Instituicao instituicao;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Modulo> modulos = new ArrayList<>();

    @ManyToMany(mappedBy = "cursos")
    private List<Usuario> usuarios = new ArrayList<>();
}
