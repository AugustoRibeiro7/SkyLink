package com.skyLink.aeroporto.view;

import com.skyLink.aeroporto.controller.VooController;
import com.skyLink.aeroporto.model.CompanhiaAerea;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class VooView {
    private final VooController controller;
    private final Scanner scanner;

    public VooView(VooController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        while (true) { // Loop infinito até o usuário escolher sair
            System.out.println("\n=== Sistema de Gerenciamento de Voos ===");
            System.out.println("1. Adicionar voo");
            System.out.println("2. Buscar voos por origem e destino");
            System.out.println("3. Modificar voo");
            System.out.println("4. Cancelar voo");
            System.out.println("5. Excluir voo");
            System.out.println("6. Listar todos os voos");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao;
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Erro: Opção inválida. Digite um número de 1 a 6.");
                continue;
            }

            switch (opcao) {
                case 1:
                    adicionarVoo();
                    break;
                case 2:
                    buscarVoos();
                    break;
                case 3:
                    modificarVoo();
                    break;
                case 4:
                    cancelarVoo();
                    break;
                case 5:
                    excluirVoo();     // ← antigo 4 vira 5
                    break;
                case 6:
                    listarVoos();
                    break;
                case 7:
                    System.out.println("Saindo do sistema...");
                    return;
                default:
                    System.out.println("Erro: Opção inválida. Escolha entre 1 e 7.");
            }
        }
    }

    private void adicionarVoo() {
        CompanhiaAerea companhiaAerea = selecionarCompanhiaAerea();
        if (companhiaAerea == null) {
            System.out.println("Operação cancelada: nenhuma companhia aérea selecionada ou disponível.");
            return;
        }

        System.out.print("Digite a origem: ");
        String origem = scanner.nextLine();
        System.out.print("Digite o destino: ");
        String destino = scanner.nextLine();
        System.out.print("Digite a data e hora do voo (formato: yyyy-MM-dd HH:mm): ");
        String dataVooInput = scanner.nextLine();
        System.out.print("Digite a duração do voo (em minutos): ");
        int duracaoVoo;
        try {
            duracaoVoo = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Duração inválida. Use apenas números.");
            return;
        }
        System.out.print("Digite a capacidade do voo: ");
        int capacidadeVoo;
        try {
            capacidadeVoo = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Capacidade inválida. Use apenas números.");
            return;
        }

        try {
            LocalDateTime dataVoo = LocalDateTime.parse(dataVooInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            controller.adicionarVoo(origem, destino, dataVoo, duracaoVoo, companhiaAerea, capacidadeVoo);
        } catch (Exception e) {
            System.out.println("Erro ao adicionar voo: " + e.getMessage());
        }
    }

    private void buscarVoos() {
        System.out.print("Digite a origem: ");
        String origem = scanner.nextLine();
        System.out.print("Digite o destino: ");
        String destino = scanner.nextLine();
        controller.buscarVoos(origem, destino);
    }

    private void modificarVoo() {
        System.out.print("Digite o ID do voo a modificar: ");
        int idVoo;
        try {
            idVoo = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID inválido. Use apenas números.");
            return;
        }

        CompanhiaAerea companhiaAerea = selecionarCompanhiaAerea();
        if (companhiaAerea == null) {
            System.out.println("Operação cancelada: nenhuma companhia aérea selecionada ou disponível.");
            return;
        }

        System.out.print("Digite a nova origem: ");
        String origem = scanner.nextLine();
        System.out.print("Digite o novo destino: ");
        String destino = scanner.nextLine();
        System.out.print("Digite a nova data e hora do voo (formato: yyyy-MM-dd HH:mm): ");
        String dataVooInput = scanner.nextLine();
        System.out.print("Digite a nova duração do voo (em minutos): ");
        int duracaoVoo;
        try {
            duracaoVoo = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Duração inválida. Use apenas números.");
            return;
        }
        System.out.print("Digite a nova capacidade do voo: ");
        int capacidadeVoo;
        try {
            capacidadeVoo = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Capacidade inválida. Use apenas números.");
            return;
        }

        try {
            LocalDateTime dataVoo = LocalDateTime.parse(dataVooInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            controller.modificarVoo(origem, destino, dataVoo, duracaoVoo, companhiaAerea, capacidadeVoo, idVoo);
        } catch (Exception e) {
            System.out.println("Erro ao modificar voo: " + e.getMessage());
        }
    }

    private void cancelarVoo() {
        System.out.print("Digite o ID do voo a cancelar: ");
        int idVoo;
        try {
            idVoo = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID inválido. Use apenas números.");
            return;
        }
        controller.cancelarVoo(idVoo);
    }

    private void excluirVoo() {
        System.out.print("Digite o ID do voo a excluir: ");
        int idVoo;
        try {
            idVoo = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID inválido. Use apenas números.");
            return;
        }
        controller.excluirVoo(idVoo);
    }

    private void listarVoos() {
        controller.listarVoos();
    }

    private CompanhiaAerea selecionarCompanhiaAerea() {
        CompanhiaAerea[] companhias = controller.listarCompanhiasAereas();
        if (companhias.length == 0) {
            System.out.println("Nenhuma companhia aérea cadastrada no sistema. Cadastre uma companhia aérea primeiro.");
            return null;
        }

        System.out.println("Companhias aéreas disponíveis:");
        for (CompanhiaAerea companhia : companhias) {
            if (companhia != null) {
                System.out.printf("ID: %d, Nome: %s, Sigla: %s%n",
                        companhia.getId(), companhia.getNome(), companhia.getSigla());
            }
        }
        System.out.print("Digite o ID da companhia aérea: ");
        int idCompanhia;
        try {
            idCompanhia = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID inválido. Use apenas números.");
            return null;
        }

        try {
            return controller.buscarCompanhiaPorId(idCompanhia);
        } catch (NoSuchElementException e) {
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
    }
}
