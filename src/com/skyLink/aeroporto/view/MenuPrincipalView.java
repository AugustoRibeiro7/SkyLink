package com.skyLink.aeroporto.view;

import com.skyLink.aeroporto.model.Passageiro;
import com.skyLink.aeroporto.model.Perfil; // <--- Importante: Importe o Enum

import java.util.Scanner;

public class MenuPrincipalView {

    private final Scanner scanner;

    private final LoginView loginView;
    private final VooView vooView;
    private final CompanhiaAereaView companhiaAereaView;
    private final TicketView ticketView;
    private final CheckInView checkInView;
    private final PassageiroView passageiroView;
    private final AeroportoView aeroportoView;

    // Construtor
    public MenuPrincipalView(
            LoginView loginView,
            VooView vooView,
            CompanhiaAereaView companhiaAereaView,
            TicketView ticketView,
            CheckInView checkInView,
            PassageiroView passageiroView,
            AeroportoView aeroportoView,
            Scanner scanner) {

        this.loginView = loginView;
        this.vooView = vooView;
        this.ticketView = ticketView;
        this.checkInView = checkInView;
        this.passageiroView = passageiroView;
        this.aeroportoView = aeroportoView;
        this.companhiaAereaView = companhiaAereaView;
        this.scanner = scanner;
    }

    public void exibirMenu() {
        int opcao;
        Passageiro usuarioLogado = null;

        do {
            System.out.println("\n=== SKYLINK - MENU PRINCIPAL ===");
            System.out.println("1 - Cadastrar-se (Novo Cliente)");
            System.out.println("2 - Login");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    passageiroView.exibirMenu(); // Cadastra novo usuário
                    break;
                case 2:
                    usuarioLogado = loginView.exibirLogin();
                    if (usuarioLogado != null) {
                        // Redireciona baseado no Perfil
                        direcionarMenuPorPerfil(usuarioLogado);
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

    // Método que decide qual tela mostrar
    private void direcionarMenuPorPerfil(Passageiro usuario) {
        if (usuario.getPerfil() == Perfil.ADMINISTRADOR) {
            exibirMenuAdmin(usuario);
        } else if (usuario.getPerfil() == Perfil.FUNCIONARIO) {
            exibirMenuFuncionario(usuario);
        } else {
            exibirMenuCliente(usuario);
        }
    }

    // === 1. MENU DO ADMINISTRADOR ===
    private void exibirMenuAdmin(Passageiro admin) {
        int opcao;
        do {
            System.out.println("\n=== PAINEL ADMINISTRATIVO (" + admin.getNome() + ") ===");
            System.out.println("1 - Gerenciar Aeroportos");
            System.out.println("2 - Gerenciar Companhias Aéreas");
            System.out.println("3 - Gerenciar Voos");
            System.out.println("4 - Gerenciar Passageiros (Listar/Banir)");
            System.out.println("0 - Logout");
            System.out.print("Escolha: ");
            opcao = lerOpcao();

            switch (opcao) {
                case 1 -> aeroportoView.exibirMenu();
                case 2 -> companhiaAereaView.exibirMenu();
                case 3 -> vooView.exibirMenu();
                case 4 -> passageiroView.exibirMenu();
                case 0 -> System.out.println("Saindo do admin...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    // === 2. MENU DO FUNCIONÁRIO ===
    private void exibirMenuFuncionario(Passageiro func) {
        int opcao;
        do {
            System.out.println("\n=== ÁREA DO FUNCIONÁRIO (" + func.getNome() + ") ===");
            System.out.println("1 - Consultar Voos");
            System.out.println("2 - Realizar Check-in (Sistema)");
            System.out.println("3 - Listar Check-ins");
            System.out.println("0 - Logout");
            System.out.print("Escolha: ");
            opcao = lerOpcao();

            switch (opcao) {
                case 1 -> vooView.exibirMenu(); // Funcionário pode ver e gerenciar voos
                case 2 -> checkInView.exibirMenuCheckIn(func);
                case 3 -> checkInView.listarTodosCheckIns();
                case 0 -> System.out.println("Saindo do painel funcionário...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    // === 3. MENU DO CLIENTE ===
    private void exibirMenuCliente(Passageiro cliente) {
        int opcao;
        do {
            System.out.println("\n=== ÁREA DO CLIENTE (" + cliente.getNome() + ") ===");
            System.out.println("1 - Comprar Passagens / Meus Tickets");
            System.out.println("2 - Realizar Check-in Online");
            System.out.println("0 - Logout");
            System.out.print("Escolha: ");
            opcao = lerOpcao();

            switch (opcao) {
                case 1 -> ticketView.exibirMenu(cliente); // Passa o cliente para ele comprar pra si mesmo
                case 2 -> checkInView.exibirMenuCheckIn(cliente);
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private int lerOpcao() {
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) return -1;
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}