package com.skyLink.aeroporto.view;

import com.skyLink.aeroporto.controller.CheckInController;
import com.skyLink.aeroporto.model.Passageiro;

import java.util.Scanner;

public class CheckInView {
    private final CheckInController controller;
    private final Scanner scanner;

    public CheckInView(CheckInController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    public void exibirMenuCheckIn(Passageiro passageiro) {
        int opcao;
        do {
            System.out.println("\n=== Sistema de Gerenciamento de Check-in ===");
            System.out.println("Usuário: " + passageiro.getNome());
            System.out.println("1. Realizar check-in");
            System.out.println("2. Listar check-ins");
            System.out.println("3. Listar cartões de embarque");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            try {
                opcao = Integer.parseInt(this.scanner.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }
            switch (opcao) {
                case 1:
                    realizarCheckIn(passageiro);
                    break;
                case 2:
                    this.controller.listarCheckIns();
                    break;
                case 3:
                    this.controller.listarCartoesEmbarque();
                    break;
                case 4:
                    System.out.println("Saindo do sistema de check-in.");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 4);
    }

    private void realizarCheckIn(Passageiro passageiro) {
        try {
            controller.listarTickets();
            System.out.print("Digite o ID do ticket: ");
            int idTicket = Integer.parseInt(this.scanner.nextLine());
            System.out.print("Digite o código do assento: ");
            int codAssento = Integer.parseInt(this.scanner.nextLine());
            this.controller.realizarCheckIn(passageiro, idTicket, codAssento);
        } catch (NumberFormatException e) {
            System.out.println("Erro: Entrada inválida. Por favor, insira números válidos.");
        }
    }
}