package br.com.ead.model.entity.instituicao;

import br.com.ead.model.entity.ensino.Curso;
import br.com.ead.model.entity.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "instituicao")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Instituicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_instituicao")
    private Long idInstituicao;

    @Column(name = "nome_instituicao")
    private String nomeInstituicao;

    @Column(name = "cpf_ou_cnpj")
    private String cpfOuCnpj;

    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @OneToMany(mappedBy = "instituicao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Usuario> usuarios = new ArrayList<>();

    @ManyToMany(mappedBy = "instituicoes")
    private Set<Curso> cursos = new HashSet<>();

    @Column(name = "quantidade_licencas_professor")
    private Integer quantidadeLicencasProfessor;

    @Column(name = "quantidade_licencas_aluno")
    private Integer quantidadeLicencasAluno;

    @Column(name = "ativo")
    private Boolean ativo;

//    public void addCurso(Curso curso) {
//        cursos.add(curso);
//        curso.setInstituicao(this);
//    }
}
