package com.skyLink.aeroporto.dao;

import com.skyLink.aeroporto.model.CompanhiaAerea;

public interface CompanhiaAereaDaoInterface {

    void salvar(CompanhiaAerea companhia);
    CompanhiaAerea buscarPorId(int id);
    CompanhiaAerea[] listarTodos();
    void atualizar(CompanhiaAerea companhia);
    void deletar(int id);
}