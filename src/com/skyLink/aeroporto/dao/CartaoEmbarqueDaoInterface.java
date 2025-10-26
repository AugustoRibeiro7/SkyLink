package com.skyLink.aeroporto.dao;

import com.skyLink.aeroporto.model.CartaoEmbarque;

public interface CartaoEmbarqueDaoInterface {
    abstract boolean inserir(CartaoEmbarque cartaoEmbarque);
    abstract boolean atualizar(CartaoEmbarque cartaoEmbarque, int identificador);
    abstract boolean deletar(int idCartaoEmbarque);
    abstract CartaoEmbarque buscar(int idCartaoEmbarque);
    abstract CartaoEmbarque[] listar();
}
