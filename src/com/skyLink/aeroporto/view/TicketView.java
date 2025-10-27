package com.skyLink.aeroporto.view;

import com.skyLink.aeroporto.controller.TicketController;
import com.skyLink.aeroporto.model.Passageiro;
import com.skyLink.aeroporto.model.Ticket;
import com.skyLink.aeroporto.model.Voo;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class TicketView {
    private final TicketController controller;
    private final Scanner scanner;

    public TicketView(TicketController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    public void exibirMenu(Passageiro passageiroLogado) {
        while (true) {
            System.out.println("\n=== Sistema de Gerenciamento de Tickets ===");
            System.out.println("1. Comprar ticket");
            System.out.println("2. Atualizar ticket");
            System.out.println("3. Excluir ticket");
            System.out.println("4. Listar todos os tickets");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao;
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Erro: Opção inválida. Digite um número de 1 a 5.");
                continue;
            }

            switch (opcao) {
                case 1:
                    comprarTicket(passageiroLogado);
                    break;
                case 2:
                    atualizarTicket(passageiroLogado);
                    break;
                case 3:
                    excluirTicket();
                    break;
                case 4:
                    listarTickets();
                    break;
                case 5:
                    System.out.println("Saindo do sistema de tickets...");
                    return;
                default:
                    System.out.println("Erro: Opção inválida. Escolha entre 1 e 5.");
            }
        }
    }

    private void comprarTicket(Passageiro passageiroLogado) {
        Voo voo = selecionarVoo();
        if (voo == null) {
            System.out.println("Operação cancelada: nenhum voo selecionado ou disponível.");
            return;
        }

        System.out.print("Digite o valor do ticket: ");
        Double valor;
        try {
            valor = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Valor inválido. Use apenas números.");
            return;
        }

        try {
            controller.comprarTicket(valor, voo, passageiroLogado);
            System.out.println("Ticket comprado para " + passageiroLogado.getNome() + " com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao comprar ticket: " + e.getMessage());
        }
    }

    private void atualizarTicket(Passageiro passageiroLogado) {
        System.out.print("Digite o ID do ticket a atualizar: ");
        int idTicket;
        try {
            idTicket = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID inválido. Use apenas números.");
            return;
        }

        Voo voo = selecionarVoo();
        if (voo == null) {
            System.out.println("Operação cancelada: nenhum voo selecionado ou disponível.");
            return;
        }

        System.out.print("Digite o novo valor do ticket: ");
        Double valor;
        try {
            valor = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Valor inválido. Use apenas números.");
            return;
        }

        try {
            controller.atualizarTicket(valor, voo, passageiroLogado, idTicket);
            System.out.println("Ticket atualizado para " + passageiroLogado.getNome() + " com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar ticket: " + e.getMessage());
        }
    }

    private void excluirTicket() {
        System.out.print("Digite o ID do ticket a excluir: ");
        int idTicket;
        try {
            idTicket = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID inválido. Use apenas números.");
            return;
        }
        controller.excluirTicket(idTicket);
    }

    private void listarTickets() {
        controller.listarTickets();
    }

    private Voo selecionarVoo() {
        Voo[] voos = controller.listarVoos();
        if (voos.length == 0) {
            System.out.println("Nenhum voo cadastrado no sistema. Cadastre um voo primeiro.");
            return null;
        }

        System.out.println("Voos disponíveis:");
        for (Voo voo : voos) {
            if (voo != null) {
                System.out.printf("ID: %d, Origem: %s, Destino: %s, Data: %s, Duração: %d min, Companhia: %s (%s), Capacidade: %d%n",
                        voo.getId(), voo.getOrigem(), voo.getDestino(), voo.getDataVoo(),
                        voo.getDuracaoVoo(), voo.getCompanhiaAerea().getNome(), voo.getCompanhiaAerea().getSigla(), voo.getCapacidadeVoo());
            }
        }
        System.out.print("Digite o ID do voo: ");
        int idVoo;
        try {
            idVoo = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID inválido. Use apenas números.");
            return null;
        }

        try {
            return controller.buscarVooPorId(idVoo);
        } catch (NoSuchElementException e) {
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
    }

}