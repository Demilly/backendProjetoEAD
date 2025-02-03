package br.com.ead.model.mapper;

import br.com.ead.controller.request.usuario.UsuarioRequest;
import br.com.ead.controller.response.usuario.UsuarioResponse;
import br.com.ead.model.entity.usuario.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {
        InstituicaoMapper.class,
        TelefoneMapper.class
})
public interface UsuarioMapper {

    @Mapping(target = "idUsuario", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "cursos", ignore = true)
    @Mapping(target = "telefones", ignore = true)
    @Mapping(target = "instituicao", ignore = true)
    Usuario toUsuario(UsuarioRequest usuarioRequest);

    UsuarioResponse toUsuarioResponse(Usuario usuario);

}
