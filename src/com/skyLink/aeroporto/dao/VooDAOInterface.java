package com.skyLink.aeroporto.dao;

import com.skyLink.aeroporto.model.Voo;

public interface VooDAOInterface {
    abstract void inserir(Voo voo);
    abstract void atualizar(Voo voo);
    abstract void deletar(Voo voo);
    abstract Voo buscar(Voo voo);
    abstract Voo[] listar();
}
