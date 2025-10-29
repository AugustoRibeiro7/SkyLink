package com.skyLink.aeroporto.dao.memory;

import com.skyLink.aeroporto.dao.VooDaoInterface;
import com.skyLink.aeroporto.model.Voo;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class VooDao implements VooDaoInterface {
    //armazenamento de Voos
    private Voo[] voos;
    private int tamanho;
    private int contId;

    //Construtor
    public VooDao() { // Inicializando vetor e posicao
        this.voos = new Voo[10];
        this.tamanho = 0;
        this.contId = 0;
    }

    @Override
    public boolean inserir(Voo voo) {
        if (voo == null) {
            throw new IllegalArgumentException("Voo não pode ser nulo");
        }
        if(this.tamanho > this.voos.length) {return false;}
        this.voos[this.tamanho] = voo;
        this.tamanho++;
        voo.setId(++contId); // Define o ID como tamanho (baseado em 1)
        return true;
    }

    @Override
    public boolean atualizar(Voo voo, int idVoo) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.voos[i] != null && this.voos[i].getId() == idVoo) {
                voo.setId(idVoo);
                voo.setDataModificacao(LocalDateTime.now());
                this.voos[i] = voo;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deletar(int idVoo) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.voos[i] != null && this.voos[i].getId() == idVoo) {
                this.voos[i] = null;
                // Reorganizar array para evitar buracos
                for (int j = i; j < this.tamanho - 1; j++) {
                    this.voos[j] = this.voos[j + 1];
                }
                this.voos[this.tamanho - 1] = null;
                this.tamanho--;
                return true;
            }
        }
        return false;
    }

    // Metodo para buscar por origem e destino do voo
    @Override
    public Voo[] buscar(String origem, String destino) {
        if(this.tamanho == 0){throw new IllegalArgumentException("Não há voos cadastrados");} // Verificando se há voos no vetor
        else if(origem == null || destino == null) {throw new IllegalArgumentException("origem e destino não podem ser nulos");} //Verificando se a origem e destino são valores validos

        Voo[] voosBuscados = new Voo[this.tamanho]; // Receberá os voos com origem e destino iguais aos buscados
        int contador = 0;
        for(int i = 0; i < this.tamanho; i++) {
            if(this.voos[i].getOrigem().equals(origem) && this.voos[i].getDestino().equals(destino)) {
                voosBuscados[contador++] = this.voos[i];
            }
        }
        if(contador == 0) {throw new NoSuchElementException("Nenhum voo encontrado com origem: "+ origem + " e destino: " + destino);} // Verificando se não foi encontrado nenhum voo com os parâmetros buscados

        return Arrays.copyOf(voosBuscados, contador); // Retornar o vetor com a quantidade de posições sendo a mesma de voos encontrados
    }

    @Override
    public Voo[] listar(){
        return Arrays.copyOf(voos, tamanho); // Retorna apenas as posições preenchidas;
    }
}
