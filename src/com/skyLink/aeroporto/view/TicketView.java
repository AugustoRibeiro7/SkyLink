package com.skyLink.aeroporto.view;

import com.skyLink.aeroporto.controller.TicketController;
import com.skyLink.aeroporto.model.Passageiro;
import com.skyLink.aeroporto.model.Ticket;
import com.skyLink.aeroporto.model.Voo;
import com.skyLink.aeroporto.model.VooAssento;

import java.util.List;
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
            System.out.println("\n=== Sistema de Compras / Tickets ===");
            System.out.println("1. Comprar ticket (Escolher Voo e Assento)");
            System.out.println("2. Listar meus tickets");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");

            int opcao;
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) { continue; }

            switch (opcao) {
                case 1:
                    comprarTicket(passageiroLogado);
                    break;
                case 2:
                    listarMeusTickets(passageiroLogado);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void comprarTicket(Passageiro passageiroLogado) {
        // 1. Selecionar Voo
        Voo voo = selecionarVoo();
        if (voo == null) return;

        // 2. Selecionar Assento (CRUCIAL: Adicionei esta parte)
        System.out.println("Buscando assentos disponíveis...");
        List<VooAssento> assentos = controller.listarAssentosLivres(voo.getId());

        if (assentos.isEmpty()) {
            System.out.println("Voo lotado! Não há assentos disponíveis.");
            return;
        }

        System.out.println("--- Assentos Livres ---");
        for (VooAssento a : assentos) {
            System.out.print("[" + a.getId() + ":" + a.getCodigoAssento() + "] ");
        }
        System.out.println();

        System.out.print("Digite o ID do assento desejado: ");
        int idAssento;
        try {
            idAssento = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        // Busca o objeto assento real
        VooAssento assentoSelecionado = null;
        for(VooAssento a : assentos) {
            if(a.getId() == idAssento) assentoSelecionado = a;
        }

        if (assentoSelecionado == null) {
            System.out.println("Assento inválido ou não pertence a este voo.");
            return;
        }

        // 3. Valor (Simulado, poderia vir do Voo)
        System.out.print("Confirme o valor do ticket (ex: 150.00): ");
        Double valor;
        try {
            valor = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) { return; }

        // 4. Finalizar
        try {
            controller.comprarTicket(valor, voo, passageiroLogado, assentoSelecionado);
            System.out.println("SUCESSO! Ticket comprado. Assento: " + assentoSelecionado.getCodigoAssento());
        } catch (Exception e) {
            System.out.println("Erro ao comprar: " + e.getMessage());
        }
    }

    private Voo selecionarVoo() {
        Voo[] voos = controller.listarVoos();
        if (voos.length == 0) {
            System.out.println("Nenhum voo disponível.");
            return null;
        }
        System.out.println("--- Voos Disponíveis ---");
        for (Voo v : voos) {
            System.out.printf("ID: %d | %s -> %s | Data: %s%n",
                    v.getId(), v.getOrigem().getSigla(), v.getDestino().getSigla(), v.getDataVoo());
        }
        System.out.print("Digite o ID do voo: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            return controller.buscarVooPorId(id);
        } catch (Exception e) {
            System.out.println("Voo não encontrado.");
            return null;
        }
    }

    private void listarMeusTickets(Passageiro passageiro) {
        System.out.println("\n=== MEUS TICKETS ===");
        List<Ticket> meusTickets = controller.listarMeusTickets(passageiro);

        if (meusTickets.isEmpty()) {
            System.out.println("Você ainda não comprou passagens.");
            return;
        }

        for (Ticket t : meusTickets) {
            // Exibe Código, Valor e Data
            System.out.printf("CÓDIGO: %s | Voo: ID: %d | Assento: ID: %d | R$ %.2f | Data: %s%n",
                    t.getCodigo(),
                    t.getVoo().getId(),
                    t.getAssento().getId(),
                    t.getValor(),
                    t.getDataCriacao() != null ? t.getDataCriacao().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "N/A"
            );
        }
    }
}