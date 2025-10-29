package com.skyLink.aeroporto.dao.memory;

import com.skyLink.aeroporto.dao.CheckInDaoInterface;
import com.skyLink.aeroporto.model.CheckIn;
import java.time.LocalDateTime;
import java.util.Arrays;

public class CheckInDao implements CheckInDaoInterface {
    private CheckIn[] checkIns;
    private int tamanho;
    private int capacidade;
    private int contId;

    public CheckInDao(int capacidade) {
        this.capacidade = capacidade;
        this.checkIns = new CheckIn[this.capacidade];
        this.tamanho = 0;
        this.contId = 0;
    }

    @Override
    public boolean inserir(CheckIn checkIn) {
        if (this.tamanho >= this.capacidade) {
            return false;
        }
        checkIn.setId(this.contId + 1);
        this.checkIns[this.tamanho] = checkIn;
        this.tamanho++;
        return true;
    }

    @Override
    public boolean atualizar(CheckIn checkIn, int identificador) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.checkIns[i] != null && this.checkIns[i].getId() == identificador) {
                checkIn.setId(identificador);
                checkIn.setDataModificacao(LocalDateTime.now());
                this.checkIns[i] = checkIn;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deletar(int idCheckIn) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.checkIns[i] != null && this.checkIns[i].getId() == idCheckIn) {
                this.checkIns[i] = null;
                // Reorganizar array para evitar buracos
                for (int j = i; j < this.tamanho - 1; j++) {
                    this.checkIns[j] = this.checkIns[j + 1];
                }
                this.checkIns[this.tamanho - 1] = null;
                this.tamanho--;
                return true;
            }
        }
        return false;
    }

    @Override
    public CheckIn buscar(int idCheckIn) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.checkIns[i] != null && this.checkIns[i].getId() == idCheckIn) {
                return this.checkIns[i];
            }
        }
        return null;
    }

    @Override
    public CheckIn[] listar() {
        return Arrays.copyOf(this.checkIns, tamanho);
    }
}