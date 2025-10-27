package com.skyLink.aeroporto.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Ticket {
    private int id;
    private Double valor;
    private Voo voo;
    private Passageiro passageiro;
    private String codigo;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;

    public Ticket(Double valor, Voo voo, Passageiro passageiro) {
        if (valor == null || valor <= 0) {
            throw new IllegalArgumentException("Valor deve ser positivo");
        }
        if (voo == null) {
            throw new IllegalArgumentException("Voo não pode ser nulo");
        }
        if (passageiro == null) {
            throw new IllegalArgumentException("Passageiro não pode ser nulo");
        }
        this.valor = valor;
        this.voo = voo;
        this.passageiro = passageiro;
        this.codigo = UUID.randomUUID().toString();
        this.dataCriacao = LocalDateTime.now();
        this.dataModificacao = this.dataCriacao;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        if (valor == null || valor <= 0) {
            throw new IllegalArgumentException("Valor deve ser positivo");
        }
        this.valor = valor;
        this.dataModificacao = LocalDateTime.now();
    }

    public Voo getVoo() {
        return voo;
    }

    public void setVoo(Voo voo) {
        if (voo == null) {
            throw new IllegalArgumentException("Voo não pode ser nulo");
        }
        this.voo = voo;
        this.dataModificacao = LocalDateTime.now();
    }

    public Passageiro getPassageiro() {
        return passageiro;
    }

    public void setPassageiro(Passageiro passageiro) {
        if (passageiro == null) {
            throw new IllegalArgumentException("Passageiro não pode ser nulo");
        }
        this.passageiro = passageiro;
        this.dataModificacao = LocalDateTime.now();
    }

    public String getCodigo() {
        return codigo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataModificacao() {
        return dataModificacao;
    }
}
