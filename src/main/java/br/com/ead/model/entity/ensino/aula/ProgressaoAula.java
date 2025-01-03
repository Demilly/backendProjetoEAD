package br.com.ead.model.entity.ensino.aula;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "progressao_aula")
public class ProgressaoAula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_progressao_aula")
    private Long idProgressaoAula;

    @Column(name = "status_concluido", nullable = false)
    private String statusConcluido;

    @Column(name = "data_ultimo_acesso")
    private LocalDateTime dataUltimoAcesso;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;

    @ManyToOne
    @JoinColumn(name = "aula_id", nullable = false)
    private Aula aula;
}
