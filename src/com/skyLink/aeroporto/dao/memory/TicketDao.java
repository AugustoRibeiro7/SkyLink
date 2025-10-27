package com.skyLink.aeroporto.dao.memory;

import com.skyLink.aeroporto.dao.TicketDaoInterface;
import com.skyLink.aeroporto.model.Ticket;

import java.util.Arrays;

public class TicketDao implements TicketDaoInterface {
    private Ticket[] tickets;
    private int tamanho;

    //Construtor
    public TicketDao() { // Inicializando vetor e posicao
        this.tickets = new Ticket[10];
        this.tamanho = 0;
    }

    @Override
    public boolean inserir(Ticket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket não pode ser nulo");
        }
         // Atribui ID sequencial (base 1..)
        tickets[tamanho] = ticket;
        tamanho++;
        ticket.setId(tamanho);
        return true;
    }

    @Override
    public boolean atualizar(Ticket ticket, int idTicket) {
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket não pode ser nulo");
        }
        for (int i = 0; i < tamanho; i++) {
            if (tickets[i] != null && tickets[i].getId() == idTicket) {
                ticket.setId(idTicket); // Mantém o mesmo ID
                tickets[i] = ticket;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deletar(int idTicket) {
        for (int i = 0; i < tamanho; i++) {
            if (tickets[i] != null && tickets[i].getId() == idTicket) {
                tickets[i] = tickets[tamanho - 1];
                tickets[tamanho - 1] = null;
                tamanho--;
                return true;
            }
        }
        return false;
    }

    @Override
    public Ticket[] listar() {
        return Arrays.copyOf(tickets, tamanho);
    }
}
