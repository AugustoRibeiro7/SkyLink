package com.skyLink.aeroporto.dao.memory;

import com.skyLink.aeroporto.dao.VooAssentoDaoInterface;
import com.skyLink.aeroporto.model.VooAssento;
import java.time.LocalDateTime;

public class VooAssentoDao implements VooAssentoDaoInterface {
    private VooAssento[] vooAssentos;
    private int tamanho;
    private int capacidade;

    public VooAssentoDao(int capacidade) {
        this.capacidade = capacidade;
        this.vooAssentos = new VooAssento[this.capacidade];
        this.tamanho = 0;
    }

    @Override
    public boolean inserir(VooAssento vooAssento) {
        if (this.tamanho >= this.capacidade) {
            return false;
        }
        vooAssento.setId(this.tamanho + 1);
        this.vooAssentos[this.tamanho] = vooAssento;
        this.tamanho++;
        return true;
    }

    @Override
    public boolean atualizar(VooAssento vooAssento, int identificador) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.vooAssentos[i] != null && this.vooAssentos[i].getId() == identificador) {
                vooAssento.setId(identificador);
                vooAssento.setDataModificacao(LocalDateTime.now());
                this.vooAssentos[i] = vooAssento;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deletar(int idVooAssento) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.vooAssentos[i] != null && this.vooAssentos[i].getId() == idVooAssento) {
                this.vooAssentos[i] = null;
                // Reorganizar array para evitar buracos
                for (int j = i; j < this.tamanho - 1; j++) {
                    this.vooAssentos[j] = this.vooAssentos[j + 1];
                }
                this.vooAssentos[this.tamanho - 1] = null;
                this.tamanho--;
                return true;
            }
        }
        return false;
    }

    @Override
    public VooAssento buscar(int idVooAssento) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.vooAssentos[i] != null && this.vooAssentos[i].getId() == idVooAssento) {
                return this.vooAssentos[i];
            }
        }
        return null;
    }

    @Override
    public VooAssento[] listar() {
        VooAssento[] resultado = new VooAssento[this.tamanho];
        for (int i = 0; i < this.tamanho; i++) {
            resultado[i] = this.vooAssentos[i];
        }
        return resultado;
    }

    @Override
    public VooAssento buscarPorVooEPassageiro(int idVoo, int idPassageiro) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.vooAssentos[i] != null && this.vooAssentos[i].getIdVoo() == idVoo && this.vooAssentos[i].getIdPassageiro() == idPassageiro) {
                return this.vooAssentos[i];
            }
        }
        return null;
    }
}