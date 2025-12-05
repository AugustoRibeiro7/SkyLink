// src/com/skyLink/aeroporto/dao/VooAssentoDaoInterface.java
package com.skyLink.aeroporto.dao;

import com.skyLink.aeroporto.model.VooAssento;
import java.util.List;

public interface VooAssentoDaoInterface {
    boolean inserir(VooAssento assento);
    List<VooAssento> listarPorVoo(int vooId);
    VooAssento buscarPorId(int id);
    boolean atualizar(VooAssento assento);
    boolean deletarPorVoo(int vooId); // ao criar voo, gera assentos automaticamente
}