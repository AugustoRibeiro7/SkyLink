package com.skyLink.aeroporto.model;

import java.time.LocalDateTime;

public class Voo {
    private int id;
    private Aeroporto origem;
    private Aeroporto destino;
    private LocalDateTime dataVoo;
    private int duracaoVoo;  // em minutos
    private CompanhiaAerea companhiaAerea;
    private int capacidadeVoo;
    private EstadoVooEnum estado;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;

    // Construtor vazio
    public Voo() {
        this.estado = EstadoVooEnum.PROGRAMADO;
    }

    // Construtor completo
    public Voo(Aeroporto origem, Aeroporto destino, LocalDateTime dataVoo,
               int duracaoVoo, CompanhiaAerea companhiaAerea, int capacidadeVoo) {
        this(); // chama o vazio → já define estado
        this.origem = origem;
        this.destino = destino;
        this.dataVoo = dataVoo;
        this.duracaoVoo = duracaoVoo;
        this.companhiaAerea = companhiaAerea;
        this.capacidadeVoo = capacidadeVoo;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Aeroporto getOrigem() { return origem; }
    public void setOrigem(Aeroporto origem) { this.origem = origem; }

    public Aeroporto getDestino() { return destino; }
    public void setDestino(Aeroporto destino) { this.destino = destino; }

    public LocalDateTime getDataVoo() { return dataVoo; }
    public void setDataVoo(LocalDateTime dataVoo) { this.dataVoo = dataVoo; }

    public int getDuracaoVoo() { return duracaoVoo; }
    public void setDuracaoVoo(int duracaoVoo) { this.duracaoVoo = duracaoVoo; }

    public CompanhiaAerea getCompanhiaAerea() { return companhiaAerea; }
    public void setCompanhiaAerea(CompanhiaAerea companhiaAerea) { this.companhiaAerea = companhiaAerea; }

    public int getCapacidadeVoo() { return capacidadeVoo; }
    public void setCapacidadeVoo(int capacidadeVoo) { this.capacidadeVoo = capacidadeVoo; }

    public EstadoVooEnum getEstado() { return estado; }
    public void setEstado(EstadoVooEnum estado) { this.estado = estado; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataModificacao() { return dataModificacao; }
    public void setDataModificacao(LocalDateTime dataModificacao) { this.dataModificacao = dataModificacao; }

    @Override
    public String toString() {
        String origemSigla = origem != null ? origem.getSigla() : "N/A";
        String destinoSigla = destino != null ? destino.getSigla() : "N/A";
        String companhiaSigla = companhiaAerea != null ? companhiaAerea.getSigla() : "N/A";

        return String.format("Voo ID:%d | %s → %s | %s | %s | %d min | %d assentos | %s",
                id, origemSigla, destinoSigla, companhiaSigla,
                dataVoo != null ? dataVoo.toLocalDate() + " " + dataVoo.toLocalTime().withNano(0) : "N/A",
                duracaoVoo, capacidadeVoo, estado);
    }
}