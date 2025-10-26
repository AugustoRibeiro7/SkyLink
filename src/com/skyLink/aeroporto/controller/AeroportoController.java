package com.skyLink.aeroporto.controller;

import com.skyLink.aeroporto.dao.memory.AeroportoDao;
import com.skyLink.aeroporto.model.Aeroporto;

import java.time.LocalDateTime;
import java.util.Scanner;

public class AeroportoController {

    private AeroportoDao aeroportoDao;
    private Scanner scanner = new Scanner(System.in);

    public AeroportoController(AeroportoDao aeroportoDao) {
        this.aeroportoDao = aeroportoDao;
    }

    public void cadastrarAeroporto() {
        System.out.println("\n=== CADASTRO DE AEROPORTO ===");
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();

        System.out.print("Sigla: ");
        String sigla = scanner.nextLine();

        Aeroporto a = new Aeroporto(id, nome, cidade, sigla, LocalDateTime.now(), LocalDateTime.now());
        aeroportoDao.salvar(a);
        System.out.println("✅ Aeroporto cadastrado com sucesso!");
    }

    public void listarAeroportos() {
        System.out.println("\n=== LISTA DE AEROPORTOS ===");
        Aeroporto[] aeroportos = aeroportoDao.listarTodos();
        for (Aeroporto a : aeroportos) {
            if (a != null) System.out.println(a);
        }
    }

    public void atualizarAeroporto() {
        System.out.println("\n=== ATUALIZAR AEROPORTO ===");
        System.out.print("ID do aeroporto: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Aeroporto existente = aeroportoDao.buscarPorId(id);
        if (existente == null) {
            System.out.println(" Aeroporto não encontrado!");
            return;
        }

        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();
        existente.setNome(nome);
        existente.setDataAtualizacao(LocalDateTime.now());
        aeroportoDao.atualizar(existente);
        System.out.println(" Aeroporto atualizado com sucesso!");
    }

    public void deletarAeroporto() {
        System.out.println("\n=== DELETAR AEROPORTO ===");
        System.out.print("ID do aeroporto: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        aeroportoDao.deletar(id);
        System.out.println("️ Aeroporto removido com sucesso!");
    }

    public void menuAeroporto() {
        int opcao;
        do {
            System.out.println("\n=== MENU AEROPORTO ===");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Deletar");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1: cadastrarAeroporto(); break;
                case 2: listarAeroportos(); break;
                case 3: atualizarAeroporto(); break;
                case 4: deletarAeroporto(); break;
                case 0: System.out.println("Voltando..."); break;
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }
}
