package com.skyLink.aeroporto.controller;

import com.skyLink.aeroporto.dao.PassageiroDaoInterface;
import com.skyLink.aeroporto.dao.TicketDaoInterface;
import com.skyLink.aeroporto.dao.memory.TicketDao;
import com.skyLink.aeroporto.model.Passageiro;
import com.skyLink.aeroporto.model.Ticket;
import com.skyLink.aeroporto.model.Voo;
import com.skyLink.aeroporto.service.TicketService;
import com.skyLink.aeroporto.service.VooService;

import java.util.NoSuchElementException;

public class TicketController {
    private final TicketService service;
    private final VooService vooService;
    private final PassageiroDaoInterface passageiroDao;

    //Construtor
    public TicketController(TicketService ticketService, VooService vooService, PassageiroDaoInterface passageiroDao) {
        this.service = ticketService;
        this.vooService =  vooService;
        this.passageiroDao = passageiroDao; //verificar por que tem esse passageiro aqui
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
        return vooService.listar();
    }

    public Voo buscarVooPorId(int id) {
        try {
            return vooService.buscarPorId(id);
        } catch (NoSuchElementException e) {
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
    }

    public Ticket buscar(int idTicket) {
        try {
            Ticket ticket = this.service.buscar(idTicket);
            if (ticket == null) {
                throw new NoSuchElementException("Ticket não encontrado com ID: " + idTicket);
            }
            return ticket;
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException("Erro ao buscar ticket: " + e.getMessage());
        }
    }
}