package com.skyLink.aeroporto.controller;

import com.skyLink.aeroporto.model.Passageiro;
import com.skyLink.aeroporto.service.LoginService;

import java.util.Scanner;

public class LoginController {

    private LoginService loginService;
    private Scanner scanner = new Scanner(System.in);

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    public Passageiro realizarLogin() {
        System.out.println("\n=== LOGIN ===");
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Passageiro p = loginService.autenticar(login, senha);
        if (p != null) {
            System.out.println(" Login realizado com sucesso! Bem-vindo, " + p.getNome() + "!");
            return p;
        } else {
            System.out.println(" Login ou senha incorretos!");
            return null;
        }
    }

    public Passageiro login(String login, String senha) {
        return loginService.autenticar(login, senha);
    }
}
