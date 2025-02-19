package br.com.ead.model.entity.s3;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoArquivo {

    DOCUMENTO(false),
    IMAGEM(true),
    VIDEO(false);

    private final boolean publicAcessible;

    public boolean isPublicAccessible() {
        return this.publicAcessible;
    }
}
