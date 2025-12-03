package com.skyLink.aeroporto.controller;

import com.skyLink.aeroporto.model.CompanhiaAerea;
import com.skyLink.aeroporto.service.CompanhiaAereaService;

public class CompanhiaAereaController {
    private final CompanhiaAereaService service;

    public CompanhiaAereaController(CompanhiaAereaService service) {
        this.service = service;
    }

    public void salvar(String nome, String sigla) {
        CompanhiaAerea c = new CompanhiaAerea();
        c.setNome(nome);
        c.setSigla(sigla);
        service.salvar(c);
    }

    public CompanhiaAerea[] listarTodos() {
        return service.listarTodos();
    }

    public CompanhiaAerea buscarPorId(int id) {
        return service.buscarPorId(id);
    }

    public void atualizar(int id, String nome, String sigla) {
        CompanhiaAerea c = service.buscarPorId(id);
        c.setNome(nome);
        c.setSigla(sigla);
        service.atualizar(c);
    }

    public void deletar(int id) {
        service.deletar(id);
    }
}