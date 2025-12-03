package com.skyLink.aeroporto.controller;

import com.skyLink.aeroporto.model.CompanhiaAerea;
import com.skyLink.aeroporto.model.Voo;
import com.skyLink.aeroporto.service.CompanhiaAereaService;
import com.skyLink.aeroporto.service.VooService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

public class VooController {

    private final VooService vooService;
    private final CompanhiaAereaService companhiaService;

    //Construtor
    public VooController(VooService vooService, CompanhiaAereaService companhiaService) {
        this.vooService = vooService;
        this.companhiaService = companhiaService;
    }

    public void adicionarVoo(String origem, String destino, LocalDateTime dataVoo,
                             int duracaoVoo, CompanhiaAerea companhiaAerea, int capacidadeVoo) {
        try {
            vooService.adicionarVoo(origem, destino, dataVoo, duracaoVoo, companhiaAerea, capacidadeVoo);
            System.out.println("Voo adicionado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao adicionar voo: " + e.getMessage());
        }
    }

    public void buscarVoos(String origem, String destino) {
        try {
            Voo[] voos = vooService.buscarVoos(origem, destino);
            if (voos.length == 0) {
                System.out.println("Nenhum voo encontrado para esse trecho.");
                return;
            }
            System.out.println("Voos encontrados:");
            for (Voo voo : voos) {
                imprimirVoo(voo);
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar voos: " + e.getMessage());
        }
    }

    public void modificarVoo(String origem, String destino, LocalDateTime dataVoo,
                             int duracaoVoo, CompanhiaAerea companhiaAerea, int capacidadeVoo, int idVoo) {
        try {
            Voo voo = new Voo(origem, destino, dataVoo, duracaoVoo, companhiaAerea, capacidadeVoo);
            boolean sucesso = vooService.modificar(voo, idVoo);
            if (sucesso) {
                System.out.println("Voo com ID " + idVoo + " modificado com sucesso!");
            } else {
                System.out.println("Falha ao modificar: voo não encontrado.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao modificar voo: " + e.getMessage());
        }
    }

    public void excluirVoo(int idVoo) {
        try {
            boolean sucesso = vooService.excluir(idVoo);
            if (sucesso) {
                System.out.println("Voo com ID " + idVoo + " excluído com sucesso!");
            } else {
                System.out.println("Falha ao excluir: voo não encontrado.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao excluir voo: " + e.getMessage());
        }
    }

    public void cancelarVoo(int idVoo) {
        try {
            vooService.cancelarVoo(idVoo);
            System.out.println("Voo com ID " + idVoo + " cancelado com sucesso!");
        } catch (IllegalArgumentException e) {
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
            System.out.println("Lista de voos:");
            for (Voo voo : voos) {
                imprimirVoo(voo);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar voos: " + e.getMessage());
        }
    }

    public Voo[] getListaVoos() {
        return vooService.listar();
    }

    public Voo buscarVooPorId(int id) {
        return vooService.buscarPorId(id);
    }

    public CompanhiaAerea[] listarCompanhiasAereas() {
        return companhiaService.listarTodos();
    }

    public CompanhiaAerea buscarCompanhiaPorId(int id) {
        return companhiaService.buscarPorId(id);
    }

    // Método auxiliar pra não repetir código
    private void imprimirVoo(Voo voo) {
        System.out.printf("ID: %d | %s → %s | %s | %s (%s) | %d assentos | Estado: %s%n",
                voo.getId(),
                voo.getOrigem(),
                voo.getDestino(),
                voo.getDataVoo().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                voo.getCompanhiaAerea().getNome(),
                voo.getCompanhiaAerea().getSigla(),
                voo.getCapacidadeVoo(),
                voo.getEstado());
    }
}