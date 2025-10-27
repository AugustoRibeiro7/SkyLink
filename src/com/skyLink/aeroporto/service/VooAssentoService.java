package com.skyLink.aeroporto.service;

import com.skyLink.aeroporto.dao.VooAssentoDaoInterface;
import com.skyLink.aeroporto.model.VooAssento;

public class VooAssentoService {
    private final VooAssentoDaoInterface dao;

    public VooAssentoService(VooAssentoDaoInterface dao) {
        this.dao = dao;
    }

    public boolean adicionarAssento(int codAssento, int idVoo, int idPassageiro) {
        if (codAssento <= 0) {
            throw new IllegalArgumentException("Código do assento deve ser positivo.");
        }
        if (idVoo <= 0 || idPassageiro <= 0) {
            throw new IllegalArgumentException("ID do voo e passageiro devem ser válidos.");
        }
        VooAssento existente = this.dao.buscarPorVooEPassageiro(idVoo, idPassageiro);
        if (existente != null) {
            throw new IllegalArgumentException("Passageiro já possui assento neste voo.");
        }
        VooAssento vooAssento = new VooAssento(0, codAssento, idVoo, idPassageiro);
        return this.dao.inserir(vooAssento);
    }

    public VooAssento buscarPorVooEPassageiro(int idVoo, int idPassageiro) {
        return this.dao.buscarPorVooEPassageiro(idVoo, idPassageiro);
    }
}