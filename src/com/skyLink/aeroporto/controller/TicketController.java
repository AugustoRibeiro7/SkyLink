package com.skyLink.aeroporto.controller;

import com.skyLink.aeroporto.model.*;
import com.skyLink.aeroporto.service.TicketService;
import com.skyLink.aeroporto.service.VooService;
import com.skyLink.aeroporto.service.VooAssentoService; // Importante

import java.util.ArrayList;
import java.util.List;

public class TicketController {
    private final TicketService ticketService;
    private final VooService vooService;
    private final VooAssentoService assentoService;

    public TicketController(TicketService ticketService, VooService vooService, VooAssentoService assentoService) {
        this.ticketService = ticketService;
        this.vooService = vooService;
        this.assentoService = assentoService;
    }

    public Voo[] listarVoos() {
        return vooService.listar(); // Retorna array para atender sua view
    }

    public Voo buscarVooPorId(int id) {
        return vooService.buscarPorId(id);
    }

    // Retorna a lista de assentos livres para mostrar no menu
    public List<VooAssento> listarAssentosLivres(int vooId) {
        return assentoService.listarLivres(vooId);
    }

    public VooAssento buscarAssento(int id) {
        return assentoService.buscarPorId(id);
    }

    public void comprarTicket(Double valor, Voo voo, Passageiro passageiro, VooAssento assento) {
        try {
            Ticket ticket = new Ticket(voo, passageiro, assento, valor);
            ticketService.comprarTicket(ticket); // O Service gera o código e salva
        } catch (Exception e) {
            throw new RuntimeException("Erro no controller: " + e.getMessage());
        }
    }

    public List<Ticket> listarMeusTickets(Passageiro passageiro) {
        try {
            return ticketService.listarTicketsPorPassageiro(passageiro);
        } catch (Exception e) {
            System.out.println("Erro ao buscar tickets: " + e.getMessage());
            return new ArrayList<>(); // Retorna lista vazia para não quebrar a tela
        }
    }

    public void atualizarTicket(Double v, Voo voo, Passageiro p, int id) { /* Implementar atualização */ }
    public void excluirTicket(int id) { /* Implementar exclusão */ }
}