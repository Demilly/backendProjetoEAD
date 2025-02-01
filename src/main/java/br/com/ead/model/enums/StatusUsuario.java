package br.com.ead.model.enums;


public enum StatusUsuario {
    ATIVO(true),
    INATIVO(false);

    private final boolean value;

    StatusUsuario(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    public static StatusUsuario fromBoolean(boolean value) {
        return value ? ATIVO : INATIVO;
    }
}
