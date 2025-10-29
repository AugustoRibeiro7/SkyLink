package com.skyLink.aeroporto.controller;

import com.skyLink.aeroporto.dao.CompanhiaAereaDaoInterface;
import com.skyLink.aeroporto.dao.VooDaoInterface;
import com.skyLink.aeroporto.dao.memory.CompanhiaAereaDao;
import com.skyLink.aeroporto.dao.memory.VooDao;
import com.skyLink.aeroporto.model.CompanhiaAerea;
import com.skyLink.aeroporto.model.Voo;
import com.skyLink.aeroporto.service.VooService;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

public class VooController {
    private final VooService service;
    private final CompanhiaAereaDaoInterface companhiaDao;

    public VooController(CompanhiaAereaDaoInterface companhiaDao) {
        VooDaoInterface dao = new VooDao();
        this.service = new VooService(dao);
        this.companhiaDao = companhiaDao;
    }

    public void adicionarVoo(String origem, String destino, LocalDateTime dataVoo, int duracaoVoo, CompanhiaAerea companhiaAerea, int capacidadeVoo) {
        try {
            service.adicionarVoo(origem, destino, dataVoo, duracaoVoo, companhiaAerea, capacidadeVoo);
            System.out.println("Voo adicionado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao adicionar voo: " + e.getMessage());
        }
    }

    public void buscarVoos(String origem, String destino) {
        try {
            Voo[] voos = service.buscarVoos(origem, destino);
            System.out.println("Voos encontrados:");
            for (Voo voo : voos) {
                System.out.printf("ID: %d, Origem: %s, Destino: %s, Data: %s, Duração: %d min, Companhia: %s (%s), Capacidade: %d%n",
                        voo.getId(), voo.getOrigem(), voo.getDestino(), voo.getDataVoo(),
                        voo.getDuracaoVoo(), voo.getCompanhiaAerea().getNome(), voo.getCompanhiaAerea().getSigla(), voo.getCapacidadeVoo());
            }
        } catch (IllegalArgumentException | IllegalStateException | NoSuchElementException e) {
            System.out.println("Erro ao buscar voos: " + e.getMessage());
        }
    }

    public void modificarVoo(String origem, String destino, LocalDateTime dataVoo, int duracaoVoo, CompanhiaAerea companhiaAerea, int capacidadeVoo, int idVoo) {
        try {
            Voo voo = new Voo(origem, destino, dataVoo, duracaoVoo, companhiaAerea, capacidadeVoo);
            boolean sucesso = service.modificar(voo, idVoo);
            if (sucesso) {
                System.out.println("Voo com ID " + idVoo + " modificado com sucesso!");
            } else {
                System.out.println("Falha ao modificar voo com ID " + idVoo + ": ID inválido ou não encontrado.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao modificar voo: " + e.getMessage());
        }
    }

    public void excluirVoo(int idVoo) {
        try {
            boolean sucesso = service.excluir(idVoo);
            if (sucesso) {
                System.out.println("Voo com ID " + idVoo + " excluído com sucesso!");
            } else {
                System.out.println("Falha ao excluir voo com ID " + idVoo + ": ID inválido ou não encontrado.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao excluir voo: " + e.getMessage());
        }
    }

    public void listarVoos() {
        try {
            Voo[] voos = service.listar();
            if (voos.length == 0) {
                System.out.println("Nenhum voo cadastrado no sistema.");
            } else {
                System.out.println("Lista de voos:");
                for (Voo voo : voos) {
                    System.out.printf("ID: %d, Origem: %s, Destino: %s, Data: %s, Duração: %d min, Companhia: %s (%s), Capacidade: %d%n",
                            voo.getId(), voo.getOrigem(), voo.getDestino(), voo.getDataVoo(),
                            voo.getDuracaoVoo(), voo.getCompanhiaAerea().getNome(), voo.getCompanhiaAerea().getSigla(), voo.getCapacidadeVoo());
                }
            }
        } catch (IllegalStateException e) {
            System.out.println("Erro ao listar voos: " + e.getMessage());
        }
    }

    public Voo[] getListaVoos() {
        return service.listar();
    }

    public Voo buscarVooPorId(int id) {
        Voo[] voos = service.listar();
        for (Voo voo : voos) {
            if (voo != null && voo.getId() == id) {
                return voo;
            }
        }
        throw new NoSuchElementException("Voo não encontrado com ID: " + id);
    }

    public CompanhiaAerea[] listarCompanhiasAereas() {
        return companhiaDao.listarTodos();
    }

    public CompanhiaAerea buscarCompanhiaPorId(int id) {
        CompanhiaAerea companhia = companhiaDao.buscarPorId(id);
        if (companhia == null) {
            throw new NoSuchElementException("Companhia aérea não encontrada com ID: " + id);
        }
        return companhia;
    }
}
