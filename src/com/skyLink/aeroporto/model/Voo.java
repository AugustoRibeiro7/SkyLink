package com.skyLink.aeroporto.model;

import java.time.LocalDateTime;

public class Voo {
    private int id;
    private String origem;
    private String destino;
    private LocalDateTime dataVoo;
    private int duracaoVoo; //tempo em minutos
    private CompanhiaAerea companhiaAerea;
    private int capacidadeVoo; //quantidade de passageiros
    private EstadoVooEnum estado; //PROGRAMADO,EMBARQUE,DECOLADO,ATRASADO,CANCELADO
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;

    //Construtor
    public Voo(String origem, String destino, LocalDateTime dataVoo, int duracaoVoo,  CompanhiaAerea companhiaAerea,int capacidadeVoo) {
        //this.id = id; Incrementado no VooDao
        this.origem = origem;
        this.destino = destino;
        this.dataVoo = dataVoo;
        this.duracaoVoo = duracaoVoo;
        this.companhiaAerea = companhiaAerea;
        this.capacidadeVoo = capacidadeVoo;
        this.estado = EstadoVooEnum.valueOf("PROGRAMADO");

        //Inicializando com a data atual
        this.dataCriacao = LocalDateTime.now();
        this.dataModificacao = this.dataCriacao;
    }

    //GETTERS e SETTERS
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public String getOrigem() {
        return origem;
    }
    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }
    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDateTime getDataVoo() {
        return dataVoo;
    }
    public void setDataVoo(LocalDateTime dataVoo) {
        this.dataVoo = dataVoo;
    }

    public int getDuracaoVoo() {
        return duracaoVoo;
    }
    public void setDuracaoVoo(int duracaoVoo) {
        this.duracaoVoo = duracaoVoo;
    }

    public CompanhiaAerea getCompanhiaAerea() {
        return companhiaAerea;
    }

    public int getCapacidadeVoo() {
        return capacidadeVoo;
    }
    public void setCapacidadeVoo(int capacidadeVoo) {
        this.capacidadeVoo = capacidadeVoo;
    }

    public EstadoVooEnum getEstado() {
        return estado;
    }
    public void setEstado(EstadoVooEnum estado){
        this.estado = estado;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataModificacao() {
        return dataModificacao;
    }
    public void setDataModificacao(LocalDateTime dataModificacao) {
        this.dataModificacao = dataModificacao;
    }

}
