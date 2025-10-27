package com.skyLink.aeroporto.controller;

import com.skyLink.aeroporto.dao.PassageiroDaoInterface;
import com.skyLink.aeroporto.dao.TicketDaoInterface;
import com.skyLink.aeroporto.dao.memory.TicketDao;
import com.skyLink.aeroporto.model.Passageiro;
import com.skyLink.aeroporto.model.Ticket;
import com.skyLink.aeroporto.model.Voo;
import com.skyLink.aeroporto.service.TicketService;

import java.util.NoSuchElementException;

public class TicketController {
    private final TicketService service;
    private final VooController vooController;
    private final PassageiroDaoInterface passageiroDao;

    public TicketController(VooController vooController, PassageiroDaoInterface passageiroDao) {
        TicketDaoInterface dao = new TicketDao();
        this.service = new TicketService(dao);
        this.vooController = vooController;
        this.passageiroDao = passageiroDao;
    }

    public void comprarTicket(Double valor, Voo voo, Passageiro passageiro) {
        try {
            service.comprarTicket(valor, voo, passageiro);
            System.out.println("Ticket comprado com sucesso! ID: " + service.listar().length);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao comprar ticket: " + e.getMessage());
        }
    }

    public void atualizarTicket(Double valor, Voo voo, Passageiro passageiro, int idTicket) {
        try {
            boolean sucesso = service.atualizarTicket(valor, voo, passageiro, idTicket);
            if (sucesso) {
                System.out.println("Ticket com ID " + idTicket + " atualizado com sucesso!");
            } else {
                System.out.println("Falha ao atualizar ticket com ID " + idTicket + ": ID inválido ou não encontrado.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao atualizar ticket: " + e.getMessage());
        }
    }

    public void excluirTicket(int idTicket) {
        try {
            boolean sucesso = service.excluir(idTicket);
            if (sucesso) {
                System.out.println("Ticket com ID " + idTicket + " excluído com sucesso!");
            } else {
                System.out.println("Falha ao excluir ticket com ID " + idTicket + ": ID inválido ou não encontrado.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao excluir ticket: " + e.getMessage());
        }
    }

    public void listarTickets() {
        try {
            Ticket[] tickets = service.listar();
            if (tickets.length == 0) {
                System.out.println("Nenhum ticket cadastrado no sistema.");
            } else {
                System.out.println("Lista de tickets:");
                for (Ticket ticket : tickets) {
                    System.out.printf("ID: %d, Código: %s, Valor: %.2f, Voo ID: %d, Passageiro: %s, Data Criação: %s%n",
                            ticket.getId(), ticket.getCodigo(), ticket.getValor(),
                            ticket.getVoo().getId(), ticket.getPassageiro().getNome(),
                            ticket.getDataCriacao());
                }
            }
        } catch (IllegalStateException e) {
            System.out.println("Erro ao listar tickets: " + e.getMessage());
        }
    }

    public Voo[] listarVoos() {
        return vooController.getListaVoos();
    }

    public Voo buscarVooPorId(int id) {
        try {
            return vooController.buscarVooPorId(id);
        } catch (NoSuchElementException e) {
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
    }

    public Passageiro[] listarPassageiros() {
        return passageiroDao.listarTodos();
    }

    public Passageiro buscarPassageiroPorId(int id) {
        Passageiro passageiro = passageiroDao.buscarPorId(id);
        if (passageiro == null) {
            throw new NoSuchElementException("Passageiro não encontrado com ID: " + id);
        }
        return passageiro;
    }
}