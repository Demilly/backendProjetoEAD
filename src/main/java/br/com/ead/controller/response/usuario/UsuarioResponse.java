package br.com.ead.controller.response.usuario;

import br.com.ead.controller.response.instituicao.InstituicaoResponse;
import br.com.ead.model.enums.TipoUsuarioEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UsuarioResponse {

    private String nome;
    private String sobrenome;
    private String email;
    private TipoUsuarioEnum tipoUsuario;
    private List<TelefoneResponse> telefones;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private InstituicaoResponse instituicao;
}
