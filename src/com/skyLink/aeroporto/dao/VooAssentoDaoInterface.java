package com.skyLink.aeroporto.dao;

import com.skyLink.aeroporto.model.VooAssento;

public interface VooAssentoDaoInterface {
    abstract boolean inserir(VooAssento voo);
    abstract boolean atualizar(VooAssento voo, int identificador);
    abstract boolean deletar(int idVooAssento);
    abstract VooAssento buscar(int idVooAssento);
    abstract VooAssento[] listar();
}
