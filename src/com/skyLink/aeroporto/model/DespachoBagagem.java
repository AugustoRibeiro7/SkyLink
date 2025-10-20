package com.skyLink.aeroporto.model;

import java.time.LocalDateTime;

public class DespachoBagagem {
    private static int nextId = 1;
    private final int id;
    private int idTicket;
    private String documento;
    private LocalDateTime dataCriacao; //ex.: "2025-10-12T15:45:00"
    private LocalDateTime dataModificacao;

    //Contrutor
    public DespachoBagagem(){
        this.id = nextId++; //Inicializando ID unico e sequencial

        //Inicializando com a data atual
        this.dataCriacao = LocalDateTime.now();
        this.dataModificacao = this.dataCriacao;
    }
}
