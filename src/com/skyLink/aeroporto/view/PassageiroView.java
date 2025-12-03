package com.skyLink.aeroporto.view;

import com.skyLink.aeroporto.controller.PassageiroController;
import com.skyLink.aeroporto.model.Passageiro;

import java.util.Scanner;

public class PassageiroView {
    private final PassageiroController controller;
    private final Scanner scanner;

    public PassageiroView(PassageiroController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n=== GERENCIAMENTO DE PASSAGEIROS ===");
            System.out.println("1. Cadastrar passageiro");
            System.out.println("2. Listar passageiros");
            System.out.println("3. Atualizar nome");
            System.out.println("4. Excluir passageiro");
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
        System.out.println("\n=== CADASTRO DE PASSAGEIRO ===");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Nascimento (AAAA-MM-DD): ");
        String nascimento = scanner.nextLine();
        System.out.print("Documento (CPF): ");
        String documento = scanner.nextLine();
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        try {
            controller.salvar(nome, nascimento, documento, login, senha);
            System.out.println("Passageiro cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listar() {
        System.out.println("\n=== PASSAGEIROS CADASTRADOS ===");
        Passageiro[] lista = controller.listarTodos();
        if (lista.length == 0) {
            System.out.println("Nenhum passageiro cadastrado.");
            return;
        }
        for (Passageiro p : lista) {
            if (p != null) {
                System.out.printf("ID: %d | %s | Login: %s | Doc: %s%n",
                        p.getId(), p.getNome(), p.getLogin(), p.getDocumento());
            }
        }
    }

    private void atualizar() {
        System.out.print("ID do passageiro: ");
        int id = lerInteiro();
        if (id <= 0) return;
        try {
            controller.buscarPorId(id); // valida existência
            System.out.print("Novo nome: ");
            String nome = scanner.nextLine();
            controller.atualizar(id, nome);
            System.out.println("Passageiro atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void excluir() {
        System.out.print("ID do passageiro para excluir: ");
        int id = lerInteiro();
        if (id <= 0) return;
        try {
            controller.deletar(id);
            System.out.println("Passageiro excluído com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
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