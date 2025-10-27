package com.skyLink.aeroporto.model;

import java.time.LocalDateTime;

public class VooAssento {
    private int id;
    private int codAssento;
    private int idVoo;
    private int idPassageiro;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;

    // Construtor
    public VooAssento(int id, int codAssento, int idVoo, int idPassageiro) {
        this.id = id;
        this.codAssento = codAssento;
        this.idVoo = idVoo;
        this.idPassageiro = idPassageiro;
        this.dataCriacao = LocalDateTime.now();
        this.dataModificacao = this.dataCriacao;
    }

    // Construtor vazio para DAO
    public VooAssento() {
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

    public int getCodAssento() {
        return this.codAssento;
    }

    public void setCodAssento(int codAssento) {
        this.codAssento = codAssento;
    }

    public int getIdVoo() {
        return this.idVoo;
    }

    public void setIdVoo(int idVoo) {
        this.idVoo = idVoo;
    }

    public int getIdPassageiro() {
        return this.idPassageiro;
    }

    public void setIdPassageiro(int idPassageiro) {
        this.idPassageiro = idPassageiro;
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