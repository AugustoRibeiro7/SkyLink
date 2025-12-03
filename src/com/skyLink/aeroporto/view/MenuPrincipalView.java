package com.skyLink.aeroporto.view;

import com.skyLink.aeroporto.controller.*;
import com.skyLink.aeroporto.dao.db.*;
import com.skyLink.aeroporto.model.Passageiro;
import com.skyLink.aeroporto.service.*;

import java.util.Scanner;

public class MenuPrincipalView {

    private final Scanner scanner;

    private final LoginView loginView;
    private final VooView vooView;
    private final CompanhiaAereaView companhiaAereaView;
    private final TicketView ticketView;
    private final CheckInView checkInView;
    private final PassageiroView passageiroView;

    // Controllers que têm menu direto (sem View separada)
    private final AeroportoController aeroportoController;

    // Construtor
    public MenuPrincipalView(
            LoginView loginView,
            VooView vooView,
            CompanhiaAereaView companhiaAereaView,
            TicketView ticketView,
            CheckInView checkInView,
            PassageiroView passageiroView,
            AeroportoController aeroportoController,
            Scanner scanner) {

        this.loginView = loginView;
        this.vooView = vooView;
        this.ticketView = ticketView;
        this.checkInView = checkInView;
        this.passageiroView = passageiroView;
        this.aeroportoController = aeroportoController;
        this.companhiaAereaView = companhiaAereaView;
        this.scanner = scanner;
    }

    public void exibirMenu() {
        int opcao;
        Passageiro usuarioLogado = null;

        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1 - Cadastrar Passageiro");
            System.out.println("2 - Login");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    passageiroView.exibirMenu();
                    break;
                case 2:
                    usuarioLogado = loginView.exibirLogin();
                    if (usuarioLogado != null) {
                        exibirMenuUsuario(usuarioLogado);
                    }
                    break;
                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void exibirMenuUsuario(Passageiro usuario) {
        int opcao;
        do {
            System.out.println("\n=== MENU DO USUÁRIO ===");
            System.out.println("Usuário: " + usuario.getNome());
            System.out.println("1 - Gerenciar Aeroportos");
            System.out.println("2 - Gerenciar Companhias Aéreas");
            System.out.println("3 - Gerenciar Passageiros");
            System.out.println("4 - Gerenciar Voos");
            System.out.println("5 - Gerenciar Passagens");
            System.out.println("6 - Gerenciar Check-in");
            System.out.println("0 - Logout");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();

            switch (opcao) {
                case 1 -> aeroportoController.menuAeroporto();
                case 2 -> companhiaAereaView.exibirMenu();
                case 3 -> passageiroView.exibirMenu();
                case 4 -> vooView.exibirMenu();
                case 5 -> ticketView.exibirMenu(usuario);
                case 6 -> checkInView.exibirMenuCheckIn(usuario);
                case 0 -> System.out.println("Logout realizado com sucesso.");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private int lerOpcao() {
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println("Nenhuma opção digitada.");
            return -1;
        }
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Opção inválida. Digite apenas números.");
            return -1;
        }
    }
}