package br.com.ead.model.entity.ensino.aula;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "video_aula")
public class VideoAula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_video_aula")
    private Long idVideoAula;

    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    private String uuid = UUID.randomUUID().toString();

    @Column(name = "url")
    private String url;

    @Column(name = "tamanho_mb")
    private Long tamanhoMb;

    @Column(name = "duracao")
    private Long duracao;

    @Column(name = "data_upload")
    private LocalDateTime dataUpload;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "aula_id")
    private AulaEntity aula;
}
