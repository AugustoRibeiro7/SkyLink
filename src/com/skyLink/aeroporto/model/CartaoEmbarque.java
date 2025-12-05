package com.skyLink.aeroporto.model;

import java.time.LocalDateTime;

public class CartaoEmbarque {
    private int id;
    private CheckIn checkIn;
    private String portao;
    private LocalDateTime dataEmissao;

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public CheckIn getCheckIn() { return checkIn; }
    public void setCheckIn(CheckIn checkIn) { this.checkIn = checkIn; }

    public String getPortao() { return portao; }
    public void setPortao(String portao) { this.portao = portao; }

    public LocalDateTime getDataEmissao() { return dataEmissao; }
    public void setDataEmissao(LocalDateTime dataEmissao) { this.dataEmissao = dataEmissao; }
}