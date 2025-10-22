package com.skyLink.aeroporto.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Ticket {
    private static int nextId = 1;
    private int id;
    private Double valor;
    private Voo voo;
    private Passageiro passageiro;
    private int codogigo;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;

    //Construtor
    public Ticket(Double valor, Voo Voo, Passageiro passageiro) {
        this.id = nextId++; //Inicializando ID único e sequencial

        this.valor = valor;
        this.voo = Voo;
        this.passageiro = passageiro;

        this.codogigo = this.id + this.voo.getId(); // Inicializando código para identificação do ticket, sendo uma concatenação de idTicket + idVoo
        //Inicializando com a data atual
        this.dataCriacao = LocalDateTime.now();
        this.dataModificacao = this.dataCriacao;
    }

}
