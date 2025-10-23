package com.skyLink.aeroporto.dao;

import com.skyLink.aeroporto.model.Voo;

public interface VooDAOInterface {
    abstract boolean inserir(Voo voo);
    abstract boolean atualizar(Voo voo);
    abstract boolean deletar(int idVoo);
    abstract Voo buscar(int idVoo);
    abstract Voo[] listar();
}
