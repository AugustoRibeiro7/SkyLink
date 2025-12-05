package com.skyLink.aeroporto.service;

import com.skyLink.aeroporto.dao.VooDaoInterface;
import com.skyLink.aeroporto.model.*;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

public class VooService {
    private final VooDaoInterface dao;

    public VooService(VooDaoInterface dao) {
        this.dao = dao;
    }

    public void adicionarVoo(Aeroporto origem, Aeroporto destino, LocalDateTime dataVoo,
                             int duracaoVoo, CompanhiaAerea companhiaAerea, int capacidadeVoo) {
        validarVoo(origem, destino, dataVoo, duracaoVoo, capacidadeVoo);

        Voo voo = new Voo(origem, destino, dataVoo, duracaoVoo, companhiaAerea, capacidadeVoo);
        if (!dao.inserir(voo)) {
            throw new RuntimeException("Falha ao inserir voo no banco de dados.");
        }
    }

    public Voo[] buscarVoos(String origemSigla, String destinoSigla) {
        return dao.buscar(origemSigla.toUpperCase(), destinoSigla.toUpperCase());
    }

    public Voo buscarPorId(int id) {
        Voo[] voos = listar();
        for (Voo voo : voos) {
            if (voo != null && voo.getId() == id) {
                return voo;
            }
        }
        throw new NoSuchElementException("Voo não encontrado com ID: " + id);
    }

    public void modificarVoo(int idVoo, Aeroporto origem, Aeroporto destino, LocalDateTime dataVoo,
                             int duracaoVoo, CompanhiaAerea companhia, int capacidade) {
        validarVoo(origem, destino, dataVoo, duracaoVoo, capacidade);

        Voo voo = new Voo(origem, destino, dataVoo, duracaoVoo, companhia, capacidade);
        voo.setId(idVoo); // necessário pro DAO saber qual atualizar

        if (!dao.atualizar(voo, idVoo)) {
            throw new IllegalArgumentException("Voo não encontrado para modificação.");
        }
    }

    public void excluir(int idVoo) {
        if (!dao.deletar(idVoo)) {
            throw new IllegalArgumentException("Não é possível excluir o voo porque já existem passagens vendidas ou assentos reservados.");
        }
    }

    public void cancelarVoo(int vooId) {
        if (!dao.atualizarEstado(vooId, EstadoVooEnum.CANCELADO)) {
            throw new IllegalArgumentException("Voo não encontrado ou já cancelado.");
        }
    }

    public void colocarEmEmbarque(int vooId) {
        if (!dao.atualizarEstado(vooId, EstadoVooEnum.EMBARQUE)) {
            throw new IllegalArgumentException("Voo não encontrado.");
        }
    }

    public Voo[] listar() {
        Voo[] voos = dao.listar();
        return voos != null ? voos : new Voo[0];
    }

    private void validarVoo(Aeroporto origem, Aeroporto destino, LocalDateTime dataVoo,
                            int duracaoVoo, int capacidadeVoo) {
        if (origem == null || destino == null) {
            throw new IllegalArgumentException("Origem e destino são obrigatórios.");
        }
        if (origem.getId() == destino.getId()) {
            throw new IllegalArgumentException("Origem e destino não podem ser o mesmo aeroporto.");
        }
        if (dataVoo == null || dataVoo.isBefore(LocalDateTime.now().plusHours(1))) {
            throw new IllegalArgumentException("Data do voo deve ser no futuro (mínimo 1h).");
        }
        if (duracaoVoo <= 0 || duracaoVoo > 1440) { // max 24h
            throw new IllegalArgumentException("Duração do voo inválida (1 a 1440 minutos).");
        }
        if (capacidadeVoo < 5 || capacidadeVoo > 100) {
            throw new IllegalArgumentException("Capacidade deve ser entre 5 e 100 assentos.");
        }
    }
}