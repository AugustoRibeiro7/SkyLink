package com.skyLink.aeroporto.dao.memory;

import com.skyLink.aeroporto.dao.VooDAOInterface;
import com.skyLink.aeroporto.model.Voo;

public class VooDAO implements VooDAOInterface {
    //armazenamento de Voos
    private Voo[] voos;
    private int posicao;

    //Construtor
    public VooDAO() {
        this.voos = new Voo[10];
        this.posicao = 0;
    }

    @Override
    public boolean inserir(Voo voo) {
        if(this.posicao > this.voos.length) {
            return false;
        }
            this.voos[this.posicao] = voo;
            this.posicao++;
            return true;
    }

    @Override
    public boolean atualizar(Voo voo) {
        return true;
    }

    @Override
    public boolean deletar(int idVoo) {
        if(this.posicao > this.voos.length || this.posicao < 0) {
            return false;
        }
        else if(this.posicao == this.voos.length) {
            this.voos[this.posicao] = null;
            return true;
        }
        // Movendo conteúdos das posições do vetor para a esquerda, substituindo a posição deletada
        for(int i = idVoo; i < this.posicao; i++) {
            this.voos[i] = this.voos[i+1];
        }
        this.voos[this.posicao] = null; // Apagando conteúdo da última posição, pois estará duplicado

        this.posicao--; // subtraindo a posição atual do vetor, pois uma foi deletada
        return true;
    }

    @Override
    public Voo buscar(int idVoo){
        return voos[idVoo];
    }

    @Override
    public Voo[] listar(){
        return voos;
    }
}
