package com.skyLink.aeroporto.view;

import com.skyLink.aeroporto.controller.CheckInController;
import com.skyLink.aeroporto.model.CartaoEmbarque;
import com.skyLink.aeroporto.model.CheckIn;
import com.skyLink.aeroporto.model.Passageiro;

import java.time.format.DateTimeFormatter;
import java.util.List;
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
            System.out.println("\n=== CHECK-IN ===");
            System.out.println("1. Realizar check-in (Via Código do Ticket)");
            System.out.println("2. Meus Cartões de Embarque");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) { opcao = -1; }

            switch (opcao) {
                case 1:
                    realizarCheckIn();
                    break;
                case 2:
                    listarMeusCartoes(passageiro);
                    break;
                case 0:
                    break; // Sai do loop
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void realizarCheckIn() {
        System.out.println("Digite o CÓDIGO do seu Ticket (ex: A1B2C): ");
        String codigo = scanner.nextLine().trim();

        if(codigo.isEmpty()) return;

        // O controller chama o service, que valida data, cria o checkin e gera o cartao
        controller.realizarCheckIn(codigo);
    }

    public void listarTodosCheckIns() {
        System.out.println("\n=== RELATÓRIO GERAL DE CHECK-INS (ÁREA DO FUNCIONÁRIO) ===");
        List<CheckIn> lista = controller.listarTodosCheckIns();

        if (lista.isEmpty()) {
            System.out.println("Nenhum check-in realizado até o momento.");
            return;
        }

        System.out.printf("%-5s | %-20s | %-10s | %-15s | %-20s%n",
                "ID", "PASSAGEIRO", "TICKET", "DATA VOO", "DATA CHECK-IN");
        System.out.println("--------------------------------------------------------------------------------");

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM HH:mm");

        for (CheckIn c : lista) {
            System.out.printf("%-5d | %-20s | %-10s | %-15s | %-20s%n",
                    c.getId(),
                    c.getTicket().getPassageiro().getNome(), // Nome do passageiro
                    c.getTicket().getCodigo(),               // Código do Ticket
                    c.getTicket().getVoo().getDataVoo().format(fmt), // Data do Voo
                    c.getDataCheckIn().format(fmt)           // Data que fez o check-in
            );
        }
    }

    private void listarMeusCartoes(Passageiro passageiro) {
        List<CartaoEmbarque> cartoes = controller.listarMeusCartoes(passageiro);

        if (cartoes.isEmpty()) {
            System.out.println("Você não possui cartões de embarque. Faça o check-in primeiro.");
            return;
        }

        DateTimeFormatter fmtData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter fmtHora = DateTimeFormatter.ofPattern("HH:mm");

        for (CartaoEmbarque ce : cartoes) {
            String nome = ce.getCheckIn().getTicket().getPassageiro().getNome();
            String origem = ce.getCheckIn().getTicket().getVoo().getOrigem().getSigla();
            String destino = ce.getCheckIn().getTicket().getVoo().getDestino().getSigla();
            String vooId = String.valueOf(ce.getCheckIn().getTicket().getVoo().getId());
            String assento = ce.getCheckIn().getTicket().getAssento().getCodigoAssento();
            String portao = ce.getPortao();
            String data = ce.getCheckIn().getTicket().getVoo().getDataVoo().format(fmtData);
            String hora = ce.getCheckIn().getTicket().getVoo().getDataVoo().format(fmtHora);

            // Desenho do Boarding Pass
            System.out.println();
            System.out.println(" | SKYLINK AIRLINES               BOARDING PASS              |");
            System.out.println(" |-----------------------------------------------------------|");
            System.out.printf(" | PASSAGEIRO: %-45s |%n", nome);
            System.out.println(" |                                                           |");
            System.out.printf(" | DE: %-3s                     PARA: %-3s                    |%n", origem, destino);
            System.out.printf(" | VOO: %-4s                   DATA: %-10s              |%n", vooId, data);
            System.out.println(" |-----------------------------------------------------------|");
            System.out.printf(" | HORÁRIO: %-5s      PORTÃO: %-3s       ASSENTO: %-3s      |%n", hora, portao, assento);
        }
    }
}