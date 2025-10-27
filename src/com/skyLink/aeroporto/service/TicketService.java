package com.skyLink.aeroporto.service;

import com.skyLink.aeroporto.dao.TicketDaoInterface;
import com.skyLink.aeroporto.model.Ticket;
import com.skyLink.aeroporto.model.Voo;
import com.skyLink.aeroporto.model.Passageiro;

public class TicketService {
    private final TicketDaoInterface dao;

    public TicketService(TicketDaoInterface dao) {
        this.dao = dao;
    }

    public void comprarTicket(Double valor, Voo voo, Passageiro passageiro) {
        if (valor == null || valor <= 0) {
            throw new IllegalArgumentException("Valor deve ser positivo");
        }
        if (voo == null) {
            throw new IllegalArgumentException("Voo não pode ser nulo");
        }
        if (passageiro == null) {
            throw new IllegalArgumentException("Passageiro não pode ser nulo");
        }
        Ticket ticket = new Ticket(valor, voo, passageiro);
        dao.inserir(ticket);
    }

    public boolean atualizarTicket(Double valor, Voo voo, Passageiro passageiro, int idTicket) {
        if (valor == null || valor <= 0) {
            throw new IllegalArgumentException("Valor deve ser positivo");
        }
        if (voo == null) {
            throw new IllegalArgumentException("Voo não pode ser nulo");
        }
        if (passageiro == null) {
            throw new IllegalArgumentException("Passageiro não pode ser nulo");
        }
        Ticket ticket = new Ticket(valor, voo, passageiro);
        return dao.atualizar(ticket, idTicket);
    }

    public boolean excluir(int idTicket) {
        if (idTicket <= 0) {
            throw new IllegalArgumentException("ID do ticket deve ser positivo");
        }
        return dao.deletar(idTicket);
    }

    public Ticket[] listar() {
        return dao.listar();
    }
}