package com.skyLink.aeroporto.service;

import com.skyLink.aeroporto.dao.VooDaoInterface;
import com.skyLink.aeroporto.model.CompanhiaAerea;
import com.skyLink.aeroporto.model.Voo;

import java.time.LocalDateTime;

public class VooService {
    private final VooDaoInterface dao;

    public VooService(VooDaoInterface dao) {
        this.dao = dao;
    }

    public void adicionarVoo(String origem, String destino, LocalDateTime dataVoo, int duracaoVoo, CompanhiaAerea companhiaAerea, int capacidadeVoo) {
        if (origem.equalsIgnoreCase(destino)) { // Validando se origem e destino são iguais
            throw new IllegalArgumentException("Origem e destino não podem ser iguais");
        }
        Voo voo = new Voo(origem, destino, dataVoo, duracaoVoo, companhiaAerea, capacidadeVoo);
        dao.inserir(voo);
    }

    public Voo[] buscarVoos(String origem, String destino) {
        return dao.buscar(origem, destino);
    }

    public boolean modificar(Voo voo, int idVoo) {
        return dao.atualizar(voo, idVoo);
    }

    public boolean excluir(int idVoo) {
        return dao.deletar(idVoo);
    }

    public Voo[] listar(){
        Voo[] voos = dao.listar();
        return voos != null ? voos : new Voo[0]; // Trata se null
    }
}
