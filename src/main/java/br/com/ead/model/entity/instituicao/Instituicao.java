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
import java.util.*;


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

    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    private String uuid = UUID.randomUUID().toString();

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

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(
            name = "curso_instituicao",
            joinColumns = @JoinColumn(name = "instituicao_id"),
            inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    private List<Curso> cursos = new ArrayList<>();

    @Column(name = "quantidade_licencas_professor")
    private Integer quantidadeLicencasProfessor;

    @Column(name = "quantidade_licencas_aluno")
    private Integer quantidadeLicencasAluno;

    @Column(name = "ativa")
    private Boolean ativa;

}
