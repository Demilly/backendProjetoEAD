package br.com.ead.model.entity.s3;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "arquivo_referencia")
public class ArquivoReferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "arquivo_referencia_id")
    private Long id;

    @Column(name = "nome_arquivo")
    private String nomeArquivo;

    @Column(name = "tipo_conteudo")
    private String contentType;

    @Column(name = "tamanho_arquivo")
    private Long contentLength;

    @Enumerated(EnumType.STRING)
    private TipoArquivo tipo;

    @Column(name = "temporario")
    private boolean temp = true;

    @Column(name = "data_criacao")
    @CreationTimestamp
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;


}
