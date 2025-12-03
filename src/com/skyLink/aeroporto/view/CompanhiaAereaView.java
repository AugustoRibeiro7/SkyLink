package com.skyLink.aeroporto.view;

import com.skyLink.aeroporto.controller.CompanhiaAereaController;
import com.skyLink.aeroporto.model.CompanhiaAerea;

import java.util.Scanner;

public class CompanhiaAereaView {
    private final CompanhiaAereaController controller;
    private final Scanner scanner = new Scanner(System.in);

    public CompanhiaAereaView(CompanhiaAereaController controller) {
        this.controller = controller;
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n=== GERENCIAMENTO DE COMPANHIAS AÉREAS ===");
            System.out.println("1. Cadastrar companhia");
            System.out.println("2. Listar companhias");
            System.out.println("3. Atualizar companhia");
            System.out.println("4. Excluir companhia");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();

            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listar();
                case 3 -> atualizar();
                case 4 -> excluir();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void cadastrar() {
        System.out.print("Nome da companhia: ");
        String nome = scanner.nextLine();
        System.out.print("Sigla (ex: LA, G3): ");
        String sigla = scanner.nextLine();

        try {
            controller.salvar(nome, sigla);
            System.out.println("Companhia cadastrada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listar() {
        System.out.println("\n=== COMPANHIAS CADASTRADAS ===");
        CompanhiaAerea[] lista = controller.listarTodos();
        if (lista.length == 0) {
            System.out.println("Nenhuma companhia cadastrada.");
            return;
        }
        for (CompanhiaAerea c : lista) {
            System.out.printf("ID: %d | %s (%s)%n", c.getId(), c.getNome(), c.getSigla());
        }
    }

    private void atualizar() {
        System.out.print("ID da companhia para atualizar: ");
        int id = lerInteiro();
        try {
            controller.buscarPorId(id); // só pra validar existência
            System.out.print("Novo nome: ");
            String nome = scanner.nextLine();
            System.out.print("Nova sigla: ");
            String sigla = scanner.nextLine();
            controller.atualizar(id, nome, sigla);
            System.out.println("Companhia atualizada com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void excluir() {
        System.out.print("ID da companhia para excluir: ");
        int id = lerInteiro();
        try {
            controller.deletar(id);
            System.out.println("Companhia excluída com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
        }
    }

    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private int lerInteiro() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return -1;
        }
    }
}