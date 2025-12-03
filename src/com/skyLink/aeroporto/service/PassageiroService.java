package com.skyLink.aeroporto.service;

import com.skyLink.aeroporto.dao.PassageiroDaoInterface;
import com.skyLink.aeroporto.model.Passageiro;

public class PassageiroService {
    private final PassageiroDaoInterface dao;

    public PassageiroService(PassageiroDaoInterface dao) {
        this.dao = dao;
    }

    public void salvar(Passageiro p) {
        validar(p);
        dao.salvar(p);
    }

    public Passageiro[] listarTodos() {
        return dao.listarTodos();
    }

    public Passageiro buscarPorId(int id) {
        Passageiro p = dao.buscarPorId(id);
        if (p == null) throw new IllegalArgumentException("Passageiro não encontrado com ID: " + id);
        return p;
    }

    public void atualizar(Passageiro p) {
        validar(p);
        if (dao.buscarPorId(p.getId()) == null) {
            throw new IllegalArgumentException("Passageiro não encontrado para atualização.");
        }
        dao.atualizar(p);
    }

    public void deletar(int id) {
        if (dao.buscarPorId(id) == null) {
            throw new IllegalArgumentException("Passageiro não encontrado para exclusão.");
        }
        dao.deletar(id);
    }

    private void validar(Passageiro p) {
        if (p.getNome() == null || p.getNome().trim().isEmpty())
            throw new IllegalArgumentException("Nome é obrigatório.");
        if (p.getLogin() == null || p.getLogin().trim().isEmpty())
            throw new IllegalArgumentException("Login é obrigatório.");
    }
}