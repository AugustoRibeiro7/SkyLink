package com.skyLink.aeroporto.controller;

import com.skyLink.aeroporto.model.*;
import com.skyLink.aeroporto.service.CompanhiaAereaService;
import com.skyLink.aeroporto.service.VooService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

public class VooController {

    private final VooService vooService;
    private final CompanhiaAereaService companhiaService;

    public VooController(VooService vooService, CompanhiaAereaService companhiaService) {
        this.vooService = vooService;
        this.companhiaService = companhiaService;
    }

    public void adicionarVoo(Aeroporto origem, Aeroporto destino, LocalDateTime dataVoo,
                             int duracaoVoo, CompanhiaAerea companhia, int capacidadeVoo) {
        try {
            vooService.adicionarVoo(origem, destino, dataVoo, duracaoVoo, companhia, capacidadeVoo);
            System.out.println("Voo adicionado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao adicionar voo: " + e.getMessage());
        }
    }

    public void buscarVoos(String origemSigla, String destinoSigla) {
        try {
            Voo[] voos = vooService.buscarVoos(origemSigla, destinoSigla);
            if (voos.length == 0) {
                System.out.println("Nenhum voo encontrado de " + origemSigla + " para " + destinoSigla + ".");
                return;
            }
            System.out.println("Voos encontrados (" + origemSigla + " → " + destinoSigla + "):");
            for (Voo voo : voos) {
                imprimirVoo(voo);
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar voos: " + e.getMessage());
        }
    }

    public void modificarVoo(int idVoo, Aeroporto origem, Aeroporto destino, LocalDateTime dataVoo,
                             int duracaoVoo, CompanhiaAerea companhia, int capacidade) {
        try {
            vooService.modificarVoo(idVoo, origem, destino, dataVoo, duracaoVoo, companhia, capacidade);
            System.out.println("Voo ID " + idVoo + " modificado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao modificar voo: " + e.getMessage());
        }
    }

    public void excluirVoo(int idVoo) {
        try {
            vooService.excluir(idVoo);
            System.out.println("Voo ID " + idVoo + " excluído com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao excluir voo: " + e.getMessage());
        }
    }

    public void cancelarVoo(int idVoo) {
        try {
            vooService.cancelarVoo(idVoo);
            System.out.println("Voo ID " + idVoo + " cancelado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cancelar voo: " + e.getMessage());
        }
    }

    public void listarVoos() {
        try {
            Voo[] voos = vooService.listar();
            if (voos.length == 0) {
                System.out.println("Nenhum voo cadastrado no sistema.");
                return;
            }
            System.out.println("=== LISTA DE VOOS ===");
            for (Voo voo : voos) {
                imprimirVoo(voo);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar voos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Voo buscarVooPorId(int id) {
        try {
            return vooService.buscarPorId(id);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public CompanhiaAerea[] listarCompanhias() {
        return companhiaService.listarTodos();
    }

    public CompanhiaAerea buscarCompanhiaPorId(int id) {
        return companhiaService.buscarPorId(id);
    }

    private void imprimirVoo(Voo voo) {
        String origem = voo.getOrigem() != null
                ? voo.getOrigem().getSigla() + " (" + voo.getOrigem().getCidade() + ")"
                : "N/A";
        String destino = voo.getDestino() != null
                ? voo.getDestino().getSigla() + " (" + voo.getDestino().getCidade() + ")"
                : "N/A";
        String companhia = voo.getCompanhiaAerea() != null
                ? voo.getCompanhiaAerea().getSigla() + " - " + voo.getCompanhiaAerea().getNome()
                : "N/A";

        System.out.printf("ID: %d | %s → %s | %s | %s | %d min | %d assentos | %s%n",
                voo.getId(),
                origem,
                destino,
                companhia,
                voo.getDataVoo().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                voo.getDuracaoVoo(),
                voo.getCapacidadeVoo(),
                voo.getEstado());
    }
}