package com.skyLink.aeroporto.model;

import java.time.LocalDateTime;

public class VooAssento {
    private static int nextId = 1;
    private int id;
    private int codAssento;
    private int idVoo;
    private int idPassageiro;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;

    //Construtor
    public VooAssento() {
        this.id = VooAssento.nextId; //Inicializando ID único e sequencial

        //Inicializando com a data atual
        this.dataCriacao = LocalDateTime.now();
        this.dataModificacao = this.dataCriacao;
    }

    //GETTERS AND SETTERS
    public int getId(){
        return this.id;
    }

    public int getCodAssento(){
        return this.getCodAssento();
    }
    public void setCodAssento(int codAssento){
        this.codAssento = codAssento;
    }

    public int getIdVoo(){
        return this.idVoo;
    }
    public void setIdVoo(int idVoo){
        this.idVoo = idVoo;
    }

    public int getIdPassageiro(){
        return this.idPassageiro;
    }
    public void setIdPassageiro(int idPassageiro){
        this.idPassageiro = idPassageiro;
    }

    public LocalDateTime getDataCriacao(){
        return this.dataCriacao;
    }
    public void setDataCriacao(LocalDateTime dataCriacao){
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataModificacao(){
        return this.dataModificacao;
    }
    public void setDataModificacao(LocalDateTime dataModificacao){
        this.dataModificacao = dataModificacao;
    }
}
