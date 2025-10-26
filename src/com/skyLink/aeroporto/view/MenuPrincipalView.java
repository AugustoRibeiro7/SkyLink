package com.skyLink.aeroporto.view;

import com.skyLink.aeroporto.controller.LoginController;
import com.skyLink.aeroporto.controller.PassageiroController;
import com.skyLink.aeroporto.controller.AeroportoController;
import com.skyLink.aeroporto.controller.CompanhiaAereaController;
import com.skyLink.aeroporto.model.Passageiro;

import java.util.Scanner;

public class MenuPrincipalView {

    private Scanner scanner = new Scanner(System.in);
    private LoginController loginController;
    private PassageiroController passageiroController;
    private AeroportoController aeroportoController;
    private CompanhiaAereaController companhiaAereaController;

    public MenuPrincipalView(
            LoginController loginController,
            PassageiroController passageiroController,
            AeroportoController aeroportoController,
            CompanhiaAereaController companhiaAereaController) {
        this.loginController = loginController;
        this.passageiroController = passageiroController;
        this.aeroportoController = aeroportoController;
        this.companhiaAereaController = companhiaAereaController;
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
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpa buffer

            switch (opcao) {
                case 1:
                    passageiroController.cadastrarPassageiro();
                    break;
                case 2:
                    usuarioLogado = loginController.realizarLogin();
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
            System.out.println("0 - Logout");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    aeroportoController.menuAeroporto();
                    break;
                case 2:
                    companhiaAereaController.menuCompanhia();
                    break;
                case 3:
                    passageiroController.menuPassageiro();
                    break;
                case 0:
                    System.out.println("Saindo do perfil...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }
}
