package com.skyLink.aeroporto.service;

import com.skyLink.aeroporto.dao.AeroportoDaoInterface;
import com.skyLink.aeroporto.model.Aeroporto;

public class AeroportoService {
    private final AeroportoDaoInterface dao;

    public AeroportoService(AeroportoDaoInterface dao) {
        this.dao = dao;
    }

    public void salvar(Aeroporto a) {
        validar(a);
        dao.salvar(a);
    }

    public Aeroporto[] listarTodos() {
        return dao.listarTodos();
    }

    public Aeroporto buscarPorId(int id) {
        Aeroporto a = dao.buscarPorId(id);
        if (a == null) {
            throw new IllegalArgumentException("Aeroporto não encontrado com ID: " + id);
        }
        return a;
    }

    public void atualizar(Aeroporto a) {
        validar(a);
        if (dao.buscarPorId(a.getId()) == null) {
            throw new IllegalArgumentException("Aeroporto não encontrado para atualização.");
        }
        dao.atualizar(a);
    }

    public void deletar(int id) {
        if (dao.buscarPorId(id) == null) {
            throw new IllegalArgumentException("Aeroporto não encontrado para exclusão.");
        }
        dao.deletar(id);
    }

    private void validar(Aeroporto a) {
        if (a.getSigla() == null || a.getSigla().trim().isEmpty() || a.getSigla().length() > 10)
            throw new IllegalArgumentException("Sigla é obrigatória e deve ter até 10 caracteres.");
        if (a.getNome() == null || a.getNome().trim().isEmpty())
            throw new IllegalArgumentException("Nome é obrigatório.");
        if (a.getCidade() == null || a.getCidade().trim().isEmpty())
            throw new IllegalArgumentException("Cidade é obrigatória.");
    }
}