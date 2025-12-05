package com.skyLink.aeroporto.model;

import java.time.LocalDateTime;

public class Ticket {
    private int id;
    private String codigo; // O localizador (Ex: "ABC12")
    private double valor;
    private Voo voo; // Objeto completo para acessar a data depois
    private Passageiro passageiro;
    private VooAssento assento;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;

    // Construtores
    public Ticket() {}

    public Ticket(Voo voo, Passageiro passageiro, VooAssento assento, double valor) {
        this.voo = voo;
        this.passageiro = passageiro;
        this.assento = assento;
        this.valor = valor;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public Voo getVoo() { return voo; }
    public void setVoo(Voo voo) { this.voo = voo; }

    public Passageiro getPassageiro() { return passageiro; }
    public void setPassageiro(Passageiro passageiro) { this.passageiro = passageiro; }

    public VooAssento getAssento() { return assento; }
    public void setAssento(VooAssento assento) { this.assento = assento; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataModificacao() { return dataModificacao; }
    public void setDataModificacao(LocalDateTime dataModificacao) { this.dataModificacao = dataModificacao; }
}