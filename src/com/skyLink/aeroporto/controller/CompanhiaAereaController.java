package com.skyLink.aeroporto.controller;

import com.skyLink.aeroporto.dao.memory.CompanhiaAereaDao;
import com.skyLink.aeroporto.model.CompanhiaAerea;

import java.time.LocalDateTime;
import java.util.Scanner;

public class CompanhiaAereaController {

    private CompanhiaAereaDao companhiaAereaDao;
    private Scanner scanner = new Scanner(System.in);

    public CompanhiaAereaController(CompanhiaAereaDao companhiaAereaDao) {
        this.companhiaAereaDao = companhiaAereaDao;
    }

    public void cadastrarCompanhia() {
        System.out.println("\n=== CADASTRO DE COMPANHIA AÉREA ===");
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Sigla: ");
        String sigla = scanner.nextLine();

        CompanhiaAerea c = new CompanhiaAerea(id, nome, sigla, LocalDateTime.now(), LocalDateTime.now());
        companhiaAereaDao.salvar(c);
        System.out.println(" Companhia Aérea cadastrada com sucesso!");
    }

    public void listarCompanhias() {
        System.out.println("\n=== LISTA DE COMPANHIAS AÉREAS ===");
        CompanhiaAerea[] companhias = companhiaAereaDao.listarTodos();
        for (CompanhiaAerea c : companhias) {
            if (c != null) System.out.println(c);
        }
    }

    public void atualizarCompanhia() {
        System.out.println("\n=== ATUALIZAR COMPANHIA AÉREA ===");
        System.out.print("ID da companhia: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        CompanhiaAerea existente = companhiaAereaDao.buscarPorId(id);
        if (existente == null) {
            System.out.println(" Companhia não encontrada!");
            return;
        }

        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();
        existente.setNome(nome);
        existente.setDataAtualizacao(LocalDateTime.now());
        companhiaAereaDao.atualizar(existente);
        System.out.println("✅ Companhia atualizada com sucesso!");
    }

    public void deletarCompanhia() {
        System.out.println("\n=== DELETAR COMPANHIA AÉREA ===");
        System.out.print("ID da companhia: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        companhiaAereaDao.deletar(id);
        System.out.println("🗑️ Companhia removida com sucesso!");
    }

    public void menuCompanhia() {
        int opcao;
        do {
            System.out.println("\n=== MENU COMPANHIA AÉREA ===");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Deletar");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1: cadastrarCompanhia(); break;
                case 2: listarCompanhias(); break;
                case 3: atualizarCompanhia(); break;
                case 4: deletarCompanhia(); break;
                case 0: System.out.println("Voltando..."); break;
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }
}
