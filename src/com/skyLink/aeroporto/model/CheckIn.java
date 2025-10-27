package com.skyLink.aeroporto.model;

import java.time.LocalDateTime;

public class CheckIn {
    private int id;
    private Ticket ticket;
    private Passageiro passageiro;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;

    // Construtor
    public CheckIn(int id, Ticket ticket, Passageiro passageiro) {
        this.id = id;
        this.ticket = ticket;
        this.passageiro = passageiro;
        this.dataCriacao = LocalDateTime.now();
        this.dataModificacao = this.dataCriacao;
    }

    // Construtor vazio para DAO
    public CheckIn() {
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

    public Ticket getTicket() {
        return this.ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Passageiro getPassageiro() {
        return this.passageiro;
    }

    public void setPassageiro(Passageiro passageiro) {
        this.passageiro = passageiro;
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