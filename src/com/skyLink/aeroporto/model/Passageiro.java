package com.skyLink.aeroporto.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Passageiro {
    private int id;
    private String nome;
    private LocalDate nascimento;
    private String documento;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public Passageiro (int id, String nome, LocalDate nascimento, String documento, LocalDateTime dataCriacao, LocalDateTime dataAtualizacao) {
        this.id = id;
        this.nome = nome;
        this.nascimento = nascimento;
        this.documento = documento;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
