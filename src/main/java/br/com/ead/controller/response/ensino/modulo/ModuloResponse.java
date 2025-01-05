package br.com.ead.controller.response.ensino.modulo;

import br.com.ead.controller.response.ensino.aula.AulaResponse;
import br.com.ead.model.entity.ensino.aula.Aula;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ModuloResponse {

    private String tituloModulo;
    private String descricao;
    private Integer ordemModulo;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private List<AulaResponse> aulas;
}


//{
//        "idCurso": 1,
//        "nome": "string",
//        "descricao": "string",
//        "isAtivo": true,
//        "urlBanner": "string",
//        "cargaHoraria": "string",
//        "dataCriacao": "2025-01-04T20:07:56.576006",
//        "dataAtualizacao": "2025-01-04T20:07:56.576006",
//        "modulos": [
//        {
//        "idModulo": 1,
//        "tituloModulo": "string",
//        "descricao": "string",
//        "ordemModulo": 0,
//        "dataCriacao": "2025-01-04T20:07:56.581005",
//        "dataAtualizacao": "2025-01-04T20:07:56.579015",
//        "aulas": [
//        {
//        "idAula": 1,
//        "titulo": "string",
//        "descricao": "string",
//        "duracaoMinutos": 0,
//        "ordemAula": 0,
//        "videos": [
//        {
//        "idVideoAula": 1,
//        "url": "string",
//        "tamanhoMb": 0,
//        "duracao": 0,
//        "dataUpload": "2025-01-04T22:08:53.15"
//        }
//        ],
//        "comentarios": [],
//        "progressaoAulas": [],
//        "questoes": [
//        {
//        "idQuestao": 1,
//        "tituloQuestao": "string",
//        "descricao": "string",
//        "respostaCorreta": "string",
//        "pontuacao": 0,
//        "dataCriacao": "2025-01-04T20:07:56.586006",
//        "dataAtualizacao": "2025-01-04T20:07:56.586006"
//        }
//        ]
//        }
//        ],
//        "notas": []
//        }
//        ]
//        }