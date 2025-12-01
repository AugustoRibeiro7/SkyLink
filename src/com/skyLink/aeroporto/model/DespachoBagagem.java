package com.skyLink.aeroporto.model;

import java.time.LocalDateTime;

public class DespachoBagagem {
    private int id;
    private Ticket ticket;
    private String documento; // ex: RG, CPF, Passaporte
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;

    public DespachoBagagem(Ticket ticket, String documento) {
        //id criado no Dao
        this.ticket = ticket;
        this.documento = documento;
        this.dataCriacao = LocalDateTime.now();
        this.dataModificacao = this.dataCriacao;
    }

    public DespachoBagagem() {
        this.dataCriacao = LocalDateTime.now();
        this.dataModificacao = this.dataCriacao;
    }

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

    public String getDocumento() {
        return this.documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
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