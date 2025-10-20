package com.skyLink.aeroporto.model;

import java.time.LocalDateTime;

public class CheckIn {
    private static int nextId = 1;
    private int id;
    private String ticket;
    private String documento;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;

    //Construtor
    public CheckIn() {
        this.id = nextId++; //Inicializando ID unico e sequencial

        //Inicializando com a data atual
        this.dataCriacao = LocalDateTime.now();
        this.dataModificacao = this.dataCriacao;
    }
}
