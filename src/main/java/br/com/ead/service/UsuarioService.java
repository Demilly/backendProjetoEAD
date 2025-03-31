package br.com.ead.service;


import br.com.ead.controller.request.usuario.UsuarioRequest;
import br.com.ead.controller.request.usuario.UsuarioUpdateRequest;
import br.com.ead.controller.response.usuario.UsuarioResponse;
import br.com.ead.model.enums.TipoUsuarioEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface UsuarioService {

    UsuarioResponse salvarUsuario(UsuarioRequest usuarioRequest);

    UsuarioResponse salvarUsuario(UsuarioRequest usuarioRequest, MultipartFile imagem);

    UsuarioResponse atualizarUsuario(String cpfCnpj, UsuarioUpdateRequest usuarioUpdateRequest, MultipartFile imagem);

    UsuarioResponse buscarUsuarioPorId(Long id);

    UsuarioResponse buscarUsuarioPorEmail(String email);

    void deletarUsuario(String cpfOuCnpj);

    Page<UsuarioResponse> buscarPorTipoUsuarioPaginado(TipoUsuarioEnum tipoUsuarioEnum, Pageable pageable);

    Page<UsuarioResponse> buscarPorTiposUsuariosAdmPaginado(Pageable pageable);

    UsuarioResponse ativarUsuario(String cpfOuCnpj);

    UsuarioResponse desativarUsuario(String cpfOuCnpj);

}
