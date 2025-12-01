package com.skyLink.aeroporto.dao;

import com.skyLink.aeroporto.model.DespachoBagagem;

public interface DespachoBagagemDaoInterface {
    abstract boolean inserir(DespachoBagagem despachoBagagem);
    abstract boolean atualizar(DespachoBagagem despachoBagagem, int identificador);
    abstract boolean deletar(int idDespachoBagagem);
    abstract DespachoBagagem buscar(int id);
    abstract DespachoBagagem[] listar();
    abstract DespachoBagagem[] listarPorTicket(int idTicket);
}
