package com.skyLink.aeroporto.model;

import java.time.LocalDateTime;

public class CartaoEmbarque {
    private int id;
    private Passageiro passageiro;
    private Voo voo;
    private Ticket ticket;
    private VooAssento vooAssento;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;

    // Construtor
    public CartaoEmbarque(int id, Passageiro passageiro, Voo voo, Ticket ticket, VooAssento vooAssento) {
        this.id = id;
        this.passageiro = passageiro;
        this.voo = voo;
        this.ticket = ticket;
        this.vooAssento = vooAssento;
        this.dataCriacao = LocalDateTime.now();
        this.dataModificacao = this.dataCriacao;
    }

    // Construtor vazio para DAO
    public CartaoEmbarque() {
        this.dataCriacao = LocalDateTime.now();
        this.dataModificacao = this.dataCriacao;
    }

    // Getters e Setters
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Passageiro getPassageiro() {
        return this.passageiro;
    }

    public void setPassageiro(Passageiro passageiro) {
        this.passageiro = passageiro;
    }

    public Voo getVoo() {
        return this.voo;
    }

    public void setVoo(Voo voo) {
        this.voo = voo;
    }

    public Ticket getTicket() {
        return this.ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public VooAssento getVooAssento() {
        return this.vooAssento;
    }

    public void setVooAssento(VooAssento vooAssento) {
        this.vooAssento = vooAssento;
    }

    public LocalDateTime getDataCriacao() {
        return this.dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataModificacao() {
        return this.dataModificacao;
    }

    public void setDataModificacao(LocalDateTime dataModificacao) {
        this.dataModificacao = dataModificacao;
    }
}