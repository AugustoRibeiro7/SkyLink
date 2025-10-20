package com.skyLink.aeroporto.dao;

import com.skyLink.aeroporto.model.Passageiro;

public interface PassageiroDAO {
    void salvar(Passageiro passageiro);
    Passageiro buscarPorId(int id);
    Passageiro[] listarTodos();
    void atualizar(Passageiro passageiro);
    void deletar(int id);
}
