package com.skyLink.aeroporto.controller;

import com.skyLink.aeroporto.model.Passageiro;
import com.skyLink.aeroporto.service.PassageiroService;

public class PassageiroController {
    private final PassageiroService service;

    public PassageiroController(PassageiroService service) {
        this.service = service;
    }

    public void salvar(String nome, String nascimento, String documento, String login, String senha) {
        Passageiro p = new Passageiro();
        p.setNome(nome);
        p.setNascimento(java.time.LocalDate.parse(nascimento));
        p.setDocumento(documento);
        p.setLogin(login);
        p.setSenha(senha);
        service.salvar(p);
    }

    public Passageiro[] listarTodos() {
        return service.listarTodos();
    }

    public Passageiro buscarPorId(int id) {
        return service.buscarPorId(id);
    }

    public void atualizar(int id, String nome) {
        Passageiro p = service.buscarPorId(id); // já valida
        p.setNome(nome);
        service.atualizar(p);
    }

    public void deletar(int id) {
        service.deletar(id);
    }
}