package com.skyLink.aeroporto.controller;

import com.skyLink.aeroporto.dao.PassageiroDaoInterface;
import com.skyLink.aeroporto.dao.CheckInDaoInterface;
import com.skyLink.aeroporto.dao.CartaoEmbarqueDaoInterface;
import com.skyLink.aeroporto.dao.VooAssentoDaoInterface;
import com.skyLink.aeroporto.dao.memory.CheckInDao;
import com.skyLink.aeroporto.dao.memory.CartaoEmbarqueDao;
import com.skyLink.aeroporto.dao.memory.VooAssentoDao;
import com.skyLink.aeroporto.model.CheckIn;
import com.skyLink.aeroporto.model.CartaoEmbarque;
import com.skyLink.aeroporto.model.Passageiro;
import com.skyLink.aeroporto.model.Ticket;
import com.skyLink.aeroporto.model.Voo;
import com.skyLink.aeroporto.service.CheckInService;
import com.skyLink.aeroporto.service.VooAssentoService;
import com.skyLink.aeroporto.service.VooService;

public class CheckInController {
    private final CheckInService service;
    private final VooService vooService;
    private final TicketController ticketController;
    private final PassageiroDaoInterface passageiroDao;

    // Construtor
    public CheckInController(CheckInService checkInService,VooService vooService, TicketController ticketController, PassageiroDaoInterface passageiroDao) {
        this.service = checkInService;
        this.vooService = vooService;
        this.ticketController = ticketController;
        this.passageiroDao = passageiroDao;
    }

    public void realizarCheckIn(Passageiro passageiro, int idTicket, int codAssento) {
        try {
            Ticket ticket = this.ticketController.buscar(idTicket);
            if (ticket == null) {
                throw new IllegalArgumentException("Ticket não encontrado com ID: " + idTicket);
            }
            Voo voo = this.vooService.buscarPorId(ticket.getVoo().getId());
            CartaoEmbarque cartao = this.service.realizarCheckIn(ticket, passageiro, voo, codAssento);
            System.out.println("Check-in realizado com sucesso! Cartão de embarque gerado: ID " + cartao.getId());
        } catch (Exception e) {
            System.out.println("Erro ao realizar check-in: " + e.getMessage());
        }
    }

    public void listarCheckIns() {
        try {
            CheckIn[] checkIns = this.service.listar();
            if (checkIns.length == 0) {
                System.out.println("Nenhum check-in cadastrado no sistema.");
            } else {
                System.out.println("Lista de check-ins:");
                for (CheckIn checkIn : checkIns) {
                    System.out.printf("ID: %d, Ticket ID: %d, Passageiro: %s, Data Criação: %s%n",
                            checkIn.getId(), checkIn.getTicket().getId(), checkIn.getPassageiro().getNome(),
                            checkIn.getDataCriacao());
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar check-ins: " + e.getMessage());
        }
    }

    public void listarCartoesEmbarque() {
        try {
            CartaoEmbarque[] cartoes = this.service.listarCartoes();
            if (cartoes.length == 0) {
                System.out.println("Nenhum cartão de embarque cadastrado no sistema.");
            } else {
                System.out.println("Lista de cartões de embarque:");
                for (CartaoEmbarque cartao : cartoes) {
                    System.out.printf("ID: %d, Passageiro: %s, Voo ID: %d, Ticket ID: %d, Assento: %d, Data Criação: %s%n",
                            cartao.getId(), cartao.getPassageiro().getNome(), cartao.getVoo().getId(),
                            cartao.getTicket().getId(), cartao.getVooAssento().getCodAssento(), cartao.getDataCriacao());
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar cartões de embarque: " + e.getMessage());
        }
    }

    public void listarTickets() {
         ticketController.listarTickets();
    }
}