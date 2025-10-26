package com.skyLink.aeroporto.dao;

import com.skyLink.aeroporto.model.Ticket;

public interface TicketDaoInterface {
    abstract boolean inserir(Ticket ticket);
    abstract boolean atualizar(Ticket ticket, int identificador);
    abstract boolean deletar(int idTicket);
    abstract Ticket buscar(int idTicket);
    abstract Ticket[] listar();
}
