package com.skyLink.aeroporto.service;

import com.skyLink.aeroporto.dao.DespachoBagagemDaoInterface;
import com.skyLink.aeroporto.model.DespachoBagagem;
import com.skyLink.aeroporto.model.Ticket;

public class DespachoBagagemService {
    private final DespachoBagagemDaoInterface dao;
    private static final int LIMITE_BAGAGENS = 2;

    public DespachoBagagemService(DespachoBagagemDaoInterface dao) {
        this.dao = dao;
    }

    public boolean despacharBagagem(Ticket ticket, String documento) {
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket não pode ser nulo.");
        }
        if (documento == null || documento.trim().isEmpty()) {
            throw new IllegalArgumentException("Documento é obrigatório.");
        }

        DespachoBagagem[] bagagens = this.dao.listarPorTicket(ticket.getId());
        if (bagagens.length >= LIMITE_BAGAGENS) {
            throw new IllegalStateException("Limite de " + LIMITE_BAGAGENS + " bagagens por passageiro atingido.");
        }

        DespachoBagagem despacho = new DespachoBagagem(ticket, documento.trim());
        return this.dao.inserir(despacho);
    }

    public DespachoBagagem[] listarPorTicket(int idTicket) {
        return this.dao.listarPorTicket(idTicket);
    }

    public DespachoBagagem[] listarTodos() {
        return this.dao.listar();
    }
}