package com.skyLink.aeroporto.dao;

import com.skyLink.aeroporto.model.Aeroporto;

public interface AeroportoDAOInterface {
    void salvar(Aeroporto aeroporto);
    Aeroporto buscarPorId(int id);
    Aeroporto[] listarTodos();
    void atualizar(Aeroporto aeroporto);
    void deletar(int id);
}
