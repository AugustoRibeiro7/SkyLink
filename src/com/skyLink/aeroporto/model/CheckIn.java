package com.skyLink.aeroporto.model;

import java.time.LocalDateTime;

public class CheckIn {
    private int id;
    private Ticket ticket;
    private LocalDateTime dataCheckIn;

    public CheckIn() {
        //vazio
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Ticket getTicket() { return ticket; }
    public void setTicket(Ticket ticket) { this.ticket = ticket; }

    public LocalDateTime getDataCheckIn() { return dataCheckIn; }
    public void setDataCheckIn(LocalDateTime dataCheckIn) { this.dataCheckIn = dataCheckIn; }
}