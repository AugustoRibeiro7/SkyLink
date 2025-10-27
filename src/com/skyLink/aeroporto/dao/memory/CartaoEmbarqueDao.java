package com.skyLink.aeroporto.dao.memory;

import com.skyLink.aeroporto.dao.CartaoEmbarqueDaoInterface;
import com.skyLink.aeroporto.model.CartaoEmbarque;
import java.time.LocalDateTime;

public class CartaoEmbarqueDao implements CartaoEmbarqueDaoInterface {
    private CartaoEmbarque[] cartoes;
    private int tamanho;
    private int capacidade;

    public CartaoEmbarqueDao(int capacidade) {
        this.capacidade = capacidade;
        this.cartoes = new CartaoEmbarque[this.capacidade];
        this.tamanho = 0;
    }

    @Override
    public boolean inserir(CartaoEmbarque cartao) {
        if (this.tamanho >= this.capacidade) {
            return false;
        }
        cartao.setId(this.tamanho + 1);
        this.cartoes[this.tamanho] = cartao;
        this.tamanho++;
        return true;
    }

    @Override
    public boolean atualizar(CartaoEmbarque cartao, int identificador) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.cartoes[i] != null && this.cartoes[i].getId() == identificador) {
                cartao.setId(identificador);
                cartao.setDataModificacao(LocalDateTime.now());
                this.cartoes[i] = cartao;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deletar(int idCartao) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.cartoes[i] != null && this.cartoes[i].getId() == idCartao) {
                this.cartoes[i] = null;
                // Reorganizar array para evitar buracos
                for (int j = i; j < this.tamanho - 1; j++) {
                    this.cartoes[j] = this.cartoes[j + 1];
                }
                this.cartoes[this.tamanho - 1] = null;
                this.tamanho--;
                return true;
            }
        }
        return false;
    }

    @Override
    public CartaoEmbarque buscar(int idCartao) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.cartoes[i] != null && this.cartoes[i].getId() == idCartao) {
                return this.cartoes[i];
            }
        }
        return null;
    }

    @Override
    public CartaoEmbarque[] listar() {
        CartaoEmbarque[] resultado = new CartaoEmbarque[this.tamanho];
        for (int i = 0; i < this.tamanho; i++) {
            resultado[i] = this.cartoes[i];
        }
        return resultado;
    }
}