package com.skyLink.aeroporto.dao.memory;

import com.skyLink.aeroporto.dao.CompanhiaAereaDaoInterface;
import com.skyLink.aeroporto.model.CompanhiaAerea;

public class CompanhiaAereaDao implements CompanhiaAereaDaoInterface {

    private CompanhiaAerea[] companhias;
    private int contador;

    public CompanhiaAereaDao(int tamanho) {
        companhias = new CompanhiaAerea[tamanho];
        contador = 0;
    }

    @Override
    public void salvar(CompanhiaAerea companhia) {
        companhias[contador++] = companhia;
    }

    @Override
    public CompanhiaAerea buscarPorId(int id) {
        for (int i = 0; i < contador; i++) {
            if (companhias[i].getId() == id) {
                return companhias[i];
            }
        }
        return null;
    }

    @Override
    public CompanhiaAerea[] listarTodos() {
        return companhias;
    }

    @Override
    public void atualizar(CompanhiaAerea companhia) {
        for (int i = 0; i < contador; i++) {
            if (companhias[i].getId() == companhia.getId()) {
                companhias[i] = companhia;
                return;
            }
        }
    }

    @Override
    public void deletar(int id) {
        for (int i = 0; i < contador; i++) {
            if (companhias[i].getId() == id) {
                companhias[i] = companhias[contador - 1]; // substitui pelo último
                companhias[contador - 1] = null;
                contador--;
                return;
            }
        }
    }
}