package com.skyLink.aeroporto.service;

import com.skyLink.aeroporto.dao.VooDaoInterface;
import com.skyLink.aeroporto.model.CompanhiaAerea;
import com.skyLink.aeroporto.model.EstadoVooEnum;
import com.skyLink.aeroporto.model.Voo;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

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

    public Voo buscarPorId(int id) {
        Voo[] voos = listar();
        for (Voo voo : voos) {
            if (voo != null && voo.getId() == id) {
                return voo;
            }
        }
        throw new NoSuchElementException("Voo não encontrado com ID: " + id);
    }

    public boolean modificar(Voo voo, int idVoo) {
        return dao.atualizar(voo, idVoo);
    }

    public boolean excluir(int idVoo) {
        try {
            return dao.deletar(idVoo);
        } catch (IllegalStateException e) {
            if ("VOO_POSSUI_TICKETS".equals(e.getMessage())) {
                throw new IllegalArgumentException("Não é possível excluir o voo porque já existem passagens vendidas.");
            }
            throw e;
        }
    }

    public void cancelarVoo(int vooId) {
        if (!dao.atualizarEstado(vooId, EstadoVooEnum.CANCELADO)) {
            throw new IllegalArgumentException("Voo não encontrado ou já cancelado.");
        }
    }

    public void colocarEmEmbarque(int vooId) {
        if (!dao.atualizarEstado(vooId, EstadoVooEnum.EMBARQUE)) {
            throw new IllegalArgumentException("Voo não encontrado.");
        }
    }

    public Voo[] listar(){
        Voo[] voos = dao.listar();
        return voos != null ? voos : new Voo[0]; // Trata se null
    }
}
