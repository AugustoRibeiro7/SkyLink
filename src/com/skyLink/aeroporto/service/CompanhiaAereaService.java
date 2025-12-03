package com.skyLink.aeroporto.service;

import com.skyLink.aeroporto.dao.CompanhiaAereaDaoInterface;
import com.skyLink.aeroporto.model.CompanhiaAerea;

public class CompanhiaAereaService {
    private final CompanhiaAereaDaoInterface dao;

    public CompanhiaAereaService(CompanhiaAereaDaoInterface dao) {
        this.dao = dao;
    }

    public void salvar(CompanhiaAerea companhia) {
        dao.salvar(companhia);
    }

    public CompanhiaAerea[] listarTodos() {
        return dao.listarTodos();
    }

    public CompanhiaAerea buscarPorId(int id) {
        CompanhiaAerea c = dao.buscarPorId(id);
        if (c == null) {
            throw new IllegalArgumentException("Companhia aérea não encontrada com ID: " + id);
        }
        return c;
    }

    public void atualizar(CompanhiaAerea companhia) {
        if (dao.buscarPorId(companhia.getId()) == null) {
            throw new IllegalArgumentException("Companhia não encontrada para atualização.");
        }
        dao.atualizar(companhia);
    }

    public void deletar(int id) {
        if (dao.buscarPorId(id) == null) {
            throw new IllegalArgumentException("Companhia não encontrada para exclusão.");
        }
        try {
            dao.deletar(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Não é possível deletar: a companhia possui voos associados.");
        }
    }
}