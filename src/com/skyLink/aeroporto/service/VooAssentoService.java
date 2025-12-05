package com.skyLink.aeroporto.service;

import com.skyLink.aeroporto.dao.VooAssentoDaoInterface;
import com.skyLink.aeroporto.model.VooAssento;
import java.util.List;

public class VooAssentoService {
    private final VooAssentoDaoInterface dao;

    public VooAssentoService(VooAssentoDaoInterface dao) {
        this.dao = dao;
    }

    public List<VooAssento> listarLivres(int vooId) {
        return dao.listarPorVoo(vooId);
    }

    public VooAssento buscarPorId(int id) {
        return dao.buscarPorId(id);
    }
}