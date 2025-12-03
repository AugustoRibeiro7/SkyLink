package com.skyLink.aeroporto.view;

import com.skyLink.aeroporto.controller.AeroportoController;
import com.skyLink.aeroporto.model.Aeroporto;

import java.util.Scanner;

public class AeroportoView {
    private final AeroportoController controller;
    private final Scanner scanner;

    public AeroportoView(AeroportoController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n=== GERENCIAMENTO DE AEROPORTOS ===");
            System.out.println("1. Cadastrar aeroporto");
            System.out.println("2. Listar aeroportos");
            System.out.println("3. Atualizar aeroporto");
            System.out.println("4. Excluir aeroporto");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();

            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listar();
                case 3 -> atualizar();
                case 4 -> excluir();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void cadastrar() {
        System.out.println("\n=== CADASTRO DE AEROPORTO ===");
        System.out.print("Sigla (ex: GRU): ");
        String sigla = scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();

        try {
            controller.salvar(sigla, nome, cidade);
            System.out.println("Aeroporto cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listar() {
        System.out.println("\n=== AEROPORTOS CADASTRADOS ===");
        Aeroporto[] lista = controller.listarTodos();
        if (lista.length == 0) {
            System.out.println("Nenhum aeroporto cadastrado.");
            return;
        }
        for (Aeroporto a : lista) {
            if (a != null) {
                System.out.printf("ID: %d | %s - %s (%s)%n",
                        a.getId(), a.getSigla(), a.getNome(), a.getCidade());
            }
        }
    }

    private void atualizar() {
        System.out.print("ID do aeroporto: ");
        int id = lerInteiro();
        if (id <= 0) return;

        try {
            controller.buscarPorId(id);
            System.out.println("Aeroporto encontrado. Digite os novos dados:");
            System.out.print("Nova sigla: ");
            String sigla = scanner.nextLine();
            System.out.print("Novo nome: ");
            String nome = scanner.nextLine();
            System.out.print("Nova cidade: ");
            String cidade = scanner.nextLine();

            controller.atualizar(id, sigla, nome, cidade);
            System.out.println("Aeroporto atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void excluir() {
        System.out.print("ID do aeroporto para excluir: ");
        int id = lerInteiro();
        if (id <= 0) return;
        try {
            controller.deletar(id);
            System.out.println("Aeroporto excluído com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    private int lerInteiro() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("ID inválido.");
            return -1;
        }
    }
}