package com.skyLink.aeroporto.dao.memory;

import com.skyLink.aeroporto.dao.TicketDaoInterface;
import com.skyLink.aeroporto.model.Ticket;

public class TicketDao implements TicketDaoInterface {
    Ticket[] tickets;
    int posicao;

    //Construtor
    public TicketDao() { // Inicializando vetor e posicao
        this.tickets = new Ticket[10];
        this.posicao = 0;
    }

    @Override
    public boolean inserir(Ticket ticket) {
        if (this.posicao >= this.tickets.length) {return false;}
        this.tickets[posicao] = ticket;
        posicao++;
        return true;
    }

    @Override
    public boolean atualizar(Ticket ticket, int idTicket) {
        if(idTicket >= this.posicao || idTicket < 0) {return false;}
        this.tickets[this.posicao] = ticket;
        return true;
    }

    @Override
    public boolean deletar(int idTicket) {
        if(idTicket < 0 || idTicket >= this.posicao) {return false;} //Verifica se o id fornecido não está fora das posições disponíveis do vetor
        else if (idTicket == this.tickets.length) { // Verifica se o id se refere a última posição disponível do vetor
            this.tickets[idTicket] = null;
        }
        else { // Movendo conteúdos das posições do vetor a partir do id a ser deletado para a esquerda, substituindo a posição deletada
            for(int i = idTicket; i < --this.posicao; i++) { //a "posicao" sempre está um valor maior que a posição do vetor atual preenchida, por isso o "--"
                this.tickets[i] = this.tickets[i+1];
            }
            this.tickets[--this.posicao] = null; // Apagando conteúdo da última posição, pois estará duplicado
        }
        // Delete realizado com sucesso para casos que caiam no "else if" ou "else"
        this.posicao--;
        return true;
    }

    @Override
    public Ticket buscar(int idTicket) {
        if (idTicket < 0 || idTicket >= this.posicao) {return null;}
        return this.tickets[idTicket];
    }

    @Override
    public Ticket[] listar() {
        if(this.posicao == 0) {return null;} // se posição for 0, nenhum objeto foi incluído ainda no vetor
        return this.tickets;
    }
}
