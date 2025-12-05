// src/com/skyLink/aeroporto/dao/TicketDaoInterface.java
package com.skyLink.aeroporto.dao;

import com.skyLink.aeroporto.model.Ticket;
import java.util.List;

public interface TicketDaoInterface {
    boolean inserir(Ticket ticket);
    boolean atualizar(Ticket ticket);
    boolean deletar(int id);
    Ticket buscarPorCodigo(String codigo);
    List<Ticket> listarPorPassageiro(int passageiroId);
    List<Ticket> listarTodos();
    Ticket buscarPorId(int id);
}