package com.skyLink.aeroporto.controller;

import com.skyLink.aeroporto.model.CartaoEmbarque;
import com.skyLink.aeroporto.model.CheckIn;
import com.skyLink.aeroporto.model.Passageiro;
import com.skyLink.aeroporto.model.Ticket;
import com.skyLink.aeroporto.service.CheckInService;
import com.skyLink.aeroporto.service.TicketService;

import java.util.ArrayList;
import java.util.List;

public class CheckInController {
    private final CheckInService checkInService;
    private final TicketService ticketService;

    public CheckInController(CheckInService checkInService, TicketService ticketService) {
        this.checkInService = checkInService;
        this.ticketService = ticketService;
    }

    public void realizarCheckIn(String codigoTicket) {
        try {
            Ticket ticket = ticketService.buscarPorCodigo(codigoTicket);
            if (ticket == null) {
                System.out.println("Ticket não encontrado com o código: " + codigoTicket);
                return;
            }

            CartaoEmbarque cartao = checkInService.realizarCheckIn(ticket);

            System.out.println("\n=== CHECK-IN REALIZADO COM SUCESSO! ===");
            System.out.println("Portão de Embarque: " + cartao.getPortao());
            System.out.println("Emissão: " + cartao.getDataEmissao());
            System.out.println("Boa viagem!");

        } catch (Exception e) {
            System.out.println("Erro ao realizar check-in: " + e.getMessage());
        }
    }

    public List<CheckIn> listarTodosCheckIns() {
        try {
            return checkInService.listarTodos();
        } catch (Exception e) {
            System.out.println("Erro ao buscar lista de check-ins: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<CartaoEmbarque> listarMeusCartoes(Passageiro passageiro) {
        try {
            return checkInService.listarCartoesPorPassageiro(passageiro);
        } catch (Exception e) {
            System.out.println("Erro ao buscar cartões: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}