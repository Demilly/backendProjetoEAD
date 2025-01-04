package br.com.ead.model.entity.ensino.aula;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    @JoinColumn(name = "aula_id", nullable = false)
    private Aula aula;
}
