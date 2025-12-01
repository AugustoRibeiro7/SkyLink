package com.skyLink.aeroporto.dao;

import com.skyLink.aeroporto.model.EstadoVooEnum;
import com.skyLink.aeroporto.model.Voo;

public interface VooDaoInterface {
    abstract boolean inserir(Voo voo);
    abstract boolean atualizar(Voo voo, int identificador);
    abstract boolean deletar(int idVoo);
    abstract Voo[] buscar(String origem, String destino);
    abstract Voo[] listar();
    boolean atualizarEstado(int vooId, EstadoVooEnum novoEstado);
}
