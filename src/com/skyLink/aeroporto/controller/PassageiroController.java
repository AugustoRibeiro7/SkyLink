package com.skyLink.aeroporto.controller;

import com.skyLink.aeroporto.dao.memory.PassageiroDao;
import com.skyLink.aeroporto.model.Passageiro;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class PassageiroController {

    private PassageiroDao passageiroDao;
    private Scanner scanner = new Scanner(System.in);

    public PassageiroController(PassageiroDao passageiroDao) {
        this.passageiroDao = passageiroDao;
    }

    public void cadastrarPassageiro() {
        System.out.println("\n=== CADASTRO DE PASSAGEIRO ===");
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Data de nascimento (AAAA-MM-DD): ");
        LocalDate nascimento = LocalDate.parse(scanner.nextLine());

        System.out.print("Documento: ");
        String documento = scanner.nextLine();

        System.out.print("Login: ");
        String login = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Passageiro p = new Passageiro(id, nome, nascimento, documento, login, senha,
                LocalDateTime.now(), LocalDateTime.now());

        passageiroDao.salvar(p);
        System.out.println("✅ Passageiro cadastrado com sucesso!");
    }

    public void listarPassageiros() {
        System.out.println("\n=== LISTA DE PASSAGEIROS ===");
        Passageiro[] passageiros = passageiroDao.listarTodos();
        for (Passageiro p : passageiros) {
            if (p != null) {
                System.out.println(p);
            }
        }
    }

    public void atualizarPassageiro() {
        System.out.println("\n=== ATUALIZAR PASSAGEIRO ===");
        System.out.print("Digite o ID do passageiro: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Passageiro existente = passageiroDao.buscarPorId(id);
        if (existente == null) {
            System.out.println(" Passageiro não encontrado!");
            return;
        }

        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();
        existente.setNome(nome);
        existente.setDataAtualizacao(LocalDateTime.now());
        passageiroDao.atualizar(existente);

        System.out.println(" Passageiro atualizado com sucesso!");
    }

    public void deletarPassageiro() {
        System.out.println("\n=== DELETAR PASSAGEIRO ===");
        System.out.print("Digite o ID do passageiro: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        passageiroDao.deletar(id);
        System.out.println("🗑️ Passageiro removido com sucesso!");
    }

    public void menuPassageiro() {
        int opcao;
        do {
            System.out.println("\n=== MENU PASSAGEIRO ===");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Deletar");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1: cadastrarPassageiro(); break;
                case 2: listarPassageiros(); break;
                case 3: atualizarPassageiro(); break;
                case 4: deletarPassageiro(); break;
                case 0: System.out.println("Voltando..."); break;
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }
}
