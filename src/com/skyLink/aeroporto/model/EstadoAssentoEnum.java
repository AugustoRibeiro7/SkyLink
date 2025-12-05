package com.skyLink.aeroporto.model;

public enum EstadoAssentoEnum {
    DISPONIVEL("DISPONIVEL"),
    RESERVADO("RESERVADO"),
    VENDIDO("VENDIDO"),
    BLOQUEADO("BLOQUEADO");

    private final String valor;

    EstadoAssentoEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static EstadoAssentoEnum fromString(String text) {
        for (EstadoAssentoEnum e : EstadoAssentoEnum.values()) {
            if (e.valor.equalsIgnoreCase(text)) {
                return e;
            }
        }
        return DISPONIVEL;
    }
}