package com.skyLink.aeroporto.view;

import com.skyLink.aeroporto.controller.AeroportoController;
import com.skyLink.aeroporto.controller.CompanhiaAereaController;
import com.skyLink.aeroporto.controller.VooController;
import com.skyLink.aeroporto.model.Aeroporto;
import com.skyLink.aeroporto.model.CompanhiaAerea;
import com.skyLink.aeroporto.model.Voo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class VooView {

    private final VooController vooController;
    private final AeroportoController aeroportoController;
    private final CompanhiaAereaController companhiaController;
    private final Scanner scanner;
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public VooView(VooController vooController,
                   AeroportoController aeroportoController,
                   CompanhiaAereaController companhiaController) {
        this.vooController = vooController;
        this.aeroportoController = aeroportoController;
        this.companhiaController = companhiaController;
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        while (true) {
            System.out.println("\n=== GERENCIAMENTO DE VOOS ===");
            System.out.println("1. Cadastrar novo voo");
            System.out.println("2. Buscar voos por trecho");
            System.out.println("3. Modificar voo");
            System.out.println("4. Cancelar voo");
            System.out.println("5. Excluir voo");
            System.out.println("6. Listar todos os voos");
            System.out.println("0. Voltar ao menu principal");
            System.out.print("Opção: ");

            int opcao = lerInteiro();
            if (opcao == -1) continue;

            switch (opcao) {
                case 1 -> cadastrarVoo();
                case 2 -> buscarVoosPorTrecho();
                case 3 -> modificarVoo();
                case 4 -> cancelarVoo();
                case 5 -> excluirVoo();
                case 6 -> vooController.listarVoos();
                case 0 -> {
                    System.out.println("Voltando ao menu principal...");
                    return;
                }
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private void cadastrarVoo() {
        System.out.println("\n=== CADASTRO DE NOVO VOO ===");

        Aeroporto origem = selecionarAeroporto("ORIGEM");
        if (origem == null) return;

        Aeroporto destino = selecionarAeroporto("DESTINO");
        if (destino == null) return;

        if (origem.getId() == destino.getId()) {
            System.out.println("ERRO: Origem e destino não podem ser o mesmo aeroporto!");
            return;
        }

        CompanhiaAerea companhia = selecionarCompanhia();
        if (companhia == null) return;

        LocalDateTime dataVoo = lerDataHora("Data e hora da partida (ex: 15/12/2025 14:30)");
        if (dataVoo == null) return;

        int duracao = lerInteiroPositivo("Duração do voo em minutos (ex: 120)");
        if (duracao == -1) return;

        int capacidade = lerInteiroPositivo("Capacidade total de assentos (50-500)");
        if (capacidade == -1) return;

        vooController.adicionarVoo(origem, destino, dataVoo, duracao, companhia, capacidade);
        System.out.printf("Voo %s → %s cadastrado com sucesso!%n", origem.getSigla(), destino.getSigla());
    }

    private void buscarVoosPorTrecho() {
        System.out.println("\n=== BUSCAR VOOS ===");
        Aeroporto origem = selecionarAeroporto("ORIGEM");
        if (origem == null) return;
        Aeroporto destino = selecionarAeroporto("DESTINO");
        if (destino == null) return;

        vooController.buscarVoos(origem.getSigla(), destino.getSigla());
    }

    private void modificarVoo() {
        System.out.print("ID do voo a modificar: ");
        int id = lerInteiro();
        if (id <= 0) return;

        Voo atual = vooController.buscarVooPorId(id);
        if (atual == null) {
            System.out.println("Voo não encontrado.");
            return;
        }

        System.out.printf("Voo atual: %s → %s | %s | %s%n",
                atual.getOrigem().getSigla(),
                atual.getDestino().getSigla(),
                atual.getCompanhiaAerea().getSigla(),
                atual.getDataVoo().format(fmt));

        System.out.println("Deixe em branco para manter o valor atual.\n");

        Aeroporto origem = selecionarAeroportoOuManter("nova ORIGEM", atual.getOrigem());
        Aeroporto destino = selecionarAeroportoOuManter("novo DESTINO", atual.getDestino());
        CompanhiaAerea companhia = selecionarCompanhiaOuManter(atual.getCompanhiaAerea());
        LocalDateTime dataVoo = lerDataHoraOuManter("nova data/hora", atual.getDataVoo());
        int duracao = lerInteiroOuManter("nova duração (min)", atual.getDuracaoVoo());
        int capacidade = lerInteiroOuManter("nova capacidade", atual.getCapacidadeVoo());

        vooController.modificarVoo(id, origem, destino, dataVoo, duracao, companhia, capacidade);
        System.out.println("Voo modificado com sucesso!");
    }

    private void cancelarVoo() {
        System.out.print("ID do voo para cancelar: ");
        int id = lerInteiro();
        if (id > 0) vooController.cancelarVoo(id);
    }

    private void excluirVoo() {
        System.out.print("ID do voo para excluir: ");
        int id = lerInteiro();
        if (id > 0) vooController.excluirVoo(id);
    }

    // ======================= MÉTODOS AUXILIARES =======================

    private Aeroporto selecionarAeroporto(String tipo) {
        System.out.printf("\n=== SELECIONE O AEROPORTO DE %s ===%n", tipo);
        Aeroporto[] lista = aeroportoController.listarTodos();
        if (lista.length == 0) {
            System.out.println("Nenhum aeroporto cadastrado!");
            return null;
        }
        for (Aeroporto a : lista) {
            System.out.printf("ID: %d | %s - %s (%s)%n", a.getId(), a.getSigla(), a.getNome(), a.getCidade());
        }
        System.out.print("Digite o ID do aeroporto: ");
        int id = lerInteiro();
        Aeroporto selecionado = aeroportoController.buscarPorId(id);
        if (selecionado == null) {
            System.out.println("Aeroporto não encontrado!");
        }
        return selecionado;
    }

    private Aeroporto selecionarAeroportoOuManter(String mensagem, Aeroporto atual) {
        System.out.printf("%s (atual: %s) [Enter = manter]: ", mensagem, atual.getSigla());
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? atual : selecionarAeroporto(mensagem);
    }

    private CompanhiaAerea selecionarCompanhia() {
        System.out.println("\n=== SELECIONE A COMPANHIA AÉREA ===");
        CompanhiaAerea[] lista = companhiaController.listarTodos();
        if (lista.length == 0) {
            System.out.println("Nenhuma companhia aérea cadastrada!");
            return null;
        }
        for (CompanhiaAerea c : lista) {
            System.out.printf("ID: %d | %s - %s%n", c.getId(), c.getSigla(), c.getNome());
        }
        System.out.print("Digite o ID da companhia: ");
        int id = lerInteiro();
        CompanhiaAerea selecionada = companhiaController.buscarPorId(id);
        if (selecionada == null) {
            System.out.println("Companhia não encontrada!");
        }
        return selecionada;
    }

    private CompanhiaAerea selecionarCompanhiaOuManter(CompanhiaAerea atual) {
        System.out.printf("Companhia (atual: %s) [Enter = manter]: ", atual.getSigla());
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? atual : selecionarCompanhia();
    }

    private LocalDateTime lerDataHora(String descricao) {
        while (true) {
            System.out.print(descricao + ": ");
            String input = scanner.nextLine().trim();
            try {
                return LocalDateTime.parse(input, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido! Use: 15/12/2025 14:30");
            }
        }
    }

    private LocalDateTime lerDataHoraOuManter(String descricao, LocalDateTime atual) {
        System.out.printf("%s (atual: %s) [Enter = manter]: ", descricao, atual.format(fmt));
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) return atual;
        try {
            return LocalDateTime.parse(input, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        } catch (DateTimeParseException e) {
            System.out.println("Formato inválido! Mantendo valor atual.");
            return atual;
        }
    }

    private int lerInteiroPositivo(String descricao) {
        while (true) {
            System.out.print(descricao + ": ");
            String input = scanner.nextLine().trim();
            try {
                int valor = Integer.parseInt(input);
                if (valor > 0) return valor;
                System.out.println("O valor deve ser maior que zero!");
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números!");
            }
        }
    }

    private int lerInteiroOuManter(String descricao, int atual) {
        System.out.printf("%s (atual: %d) [Enter = manter]: ", descricao, atual);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) return atual;
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido! Mantendo atual.");
            return atual;
        }
    }

    private int lerInteiro() {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida! Digite um número: ");
            }
        }
    }
}