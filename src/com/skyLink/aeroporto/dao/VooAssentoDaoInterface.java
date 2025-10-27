package com.skyLink.aeroporto.dao;

import com.skyLink.aeroporto.model.VooAssento;

public interface VooAssentoDaoInterface {
    abstract boolean inserir(VooAssento vooAssento);
    abstract boolean atualizar(VooAssento vooAssento, int identificador);
    abstract boolean deletar(int idVooAssento);
    abstract VooAssento buscar(int idVooAssento);
    abstract VooAssento[] listar();
    abstract VooAssento buscarPorVooEPassageiro(int idVoo, int idPassageiro);
}