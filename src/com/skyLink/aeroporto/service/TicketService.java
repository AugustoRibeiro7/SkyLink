package com.skyLink.aeroporto.service;

import com.skyLink.aeroporto.dao.db.TicketDaoMysql;
import com.skyLink.aeroporto.model.Passageiro;
import com.skyLink.aeroporto.model.Ticket;
import com.skyLink.aeroporto.model.VooAssento;

import java.util.Collections;
import java.util.List;

public class TicketService {
    private final TicketDaoMysql dao;
    private final VooAssentoService assentoService;

    public TicketService(TicketDaoMysql dao, VooAssentoService assentoService) {
        this.dao = dao;
        this.assentoService = assentoService;
    }

    public void comprarTicket(Ticket ticket) {
        // Validações básicas
        if (ticket.getVoo() == null || ticket.getPassageiro() == null) {
            throw new IllegalArgumentException("Dados incompletos para compra.");
        }

        // Verifica se o assento ainda está livre (redundância de segurança)
        VooAssento assento = assentoService.buscarPorId(ticket.getAssento().getId());
        if (assento == null) {
            throw new IllegalArgumentException("Assento inválido.");
        }

        // Tenta inserir
        if (!dao.inserir(ticket)) {
            throw new RuntimeException("Não foi possível processar a compra.");
        }
    }

    public Ticket buscarPorCodigo(String codigo) {
        return dao.buscarPorCodigo(codigo);
    }

    public List<Ticket> listarTicketsPorPassageiro(Passageiro passageiro) {
        if (passageiro == null) {
            return Collections.emptyList();
        }

        return dao.listarPorPassageiro(passageiro.getId());
    }
}