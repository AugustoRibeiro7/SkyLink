package com.skyLink.aeroporto.model;

import java.time.LocalDateTime;

public class Aeroporto {
    private int id;
    private String nome;
    private String cidade;
    private String sigla;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public Aeroporto(int id, String nome, String cidade, String sigla, LocalDateTime dataCriacao, LocalDateTime dataAtualizacao) {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.sigla = sigla;
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
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

    @Override
    public String toString() {
        return "Aeroporto {" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cidade='" + cidade + '\'' +
                ", sigla='" + sigla + '\'' +
                ", criado=" + dataCriacao +
                ", atualizado=" + dataAtualizacao +
                '}';
    }
}
