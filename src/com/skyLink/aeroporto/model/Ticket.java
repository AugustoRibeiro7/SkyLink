package com.skyLink.aeroporto.model;

import java.time.LocalDateTime;

public class Ticket {
    private static int nextId = 1;
    private int id;
    private Double valor;
    private Voo voo;
    //private Passageiro passageiro;
    private String codogigo;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;

    //Construtor
    public Ticket() {
        this.id = nextId++; //Inicializando ID unico e sequencial

        //Inicializando com a data atual
        this.dataCriacao = LocalDateTime.now();
        this.dataModificacao = this.dataCriacao;
    }

}
