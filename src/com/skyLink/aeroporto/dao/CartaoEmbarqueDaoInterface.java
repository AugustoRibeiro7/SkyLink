package com.skyLink.aeroporto.dao;

import com.skyLink.aeroporto.model.CartaoEmbarque;

public interface CartaoEmbarqueDaoInterface {
    abstract boolean inserir(CartaoEmbarque cartao);
    abstract boolean atualizar(CartaoEmbarque cartao, int identificador);
    abstract boolean deletar(int idCartao);
    abstract CartaoEmbarque buscar(int idCartao);
    abstract CartaoEmbarque[] listar();
}