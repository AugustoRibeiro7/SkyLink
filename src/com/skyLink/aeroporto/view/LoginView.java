package com.skyLink.aeroporto.view;

import com.skyLink.aeroporto.controller.LoginController;
import com.skyLink.aeroporto.model.Passageiro;

import java.util.Scanner;

public class LoginView {
    private LoginController loginController;
    private Scanner scanner;

    public LoginView(LoginController loginController) {
        this.loginController = loginController;
        this.scanner = new Scanner(System.in);
    }

    public Passageiro exibirLogin() {
        System.out.println("=== LOGIN ===");
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        // 1. View envia dados para o Controller
        Passageiro passageiro = loginController.login(login, senha);

        // 2. View exibe o resultado
        if (passageiro != null) {
            System.out.println("Login realizado com sucesso! Bem-vindo, " + passageiro.getNome() + "!");
            return passageiro;
        } else {
            System.out.println("Login ou senha incorretos!");
            return null;
        }
    }
}