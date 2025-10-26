package com.skyLink.aeroporto.controller;

import com.skyLink.aeroporto.model.Passageiro;
import com.skyLink.aeroporto.service.LoginService;

public class LoginController {

    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    public Passageiro login(String login, String senha) {
        return loginService.autenticar(login, senha);
    }
}