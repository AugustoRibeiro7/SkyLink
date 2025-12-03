package com.skyLink.aeroporto.controller;

import com.skyLink.aeroporto.model.Aeroporto;
import com.skyLink.aeroporto.service.AeroportoService;

public class AeroportoController {
    private final AeroportoService service;

    public AeroportoController(AeroportoService service) {
        this.service = service;
    }

    public void salvar(String sigla, String nome, String cidade) {
        Aeroporto a = new Aeroporto();
        a.setSigla(sigla.toUpperCase().trim());
        a.setNome(nome.trim());
        a.setCidade(cidade.trim());
        service.salvar(a);
    }

    public Aeroporto[] listarTodos() {
        return service.listarTodos();
    }

    public Aeroporto buscarPorId(int id) {
        return service.buscarPorId(id);
    }

    public void atualizar(int id, String sigla, String nome, String cidade) {
        Aeroporto a = service.buscarPorId(id);
        a.setSigla(sigla.toUpperCase().trim());
        a.setNome(nome.trim());
        a.setCidade(cidade.trim());
        service.atualizar(a);
    }

    public void deletar(int id) {
        service.deletar(id);
    }
}