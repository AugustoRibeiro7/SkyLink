package com.skyLink.aeroporto.model;

import java.time.LocalDateTime;

public class VooAssento {
    private int id;
    private int vooId;
    private String codigoAssento; // Ex: "1A"
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getVooId() { return vooId; }
    public void setVooId(int vooId) { this.vooId = vooId; }

    public String getCodigoAssento() { return codigoAssento; }
    public void setCodigoAssento(String codigoAssento) { this.codigoAssento = codigoAssento; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataModificacao() { return dataModificacao; }
    public void setDataModificacao(LocalDateTime dataModificacao) { this.dataModificacao = dataModificacao; }

    @Override
    public String toString() {
        return codigoAssento;
    }
}