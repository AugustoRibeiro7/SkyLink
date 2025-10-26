package com.skyLink.aeroporto.dao.memory;

import com.skyLink.aeroporto.dao.VooAssentoDaoInterface;
import com.skyLink.aeroporto.model.VooAssento;

public class VooAssentoDao implements VooAssentoDaoInterface {
    private VooAssento[] vooAssentos;
    private int posicao; //sempre estará um número maior do que a última posição do vetor com um objeto

    //Construtor
    public VooAssentoDao() { // Inicializando vetor e posicao
        this.vooAssentos = new VooAssento[10];
        this.posicao = 0;
    }

    @Override
    public boolean inserir(VooAssento vooAssento) {
        if(this.posicao > this.vooAssentos.length) {return false;}
        this.vooAssentos[this.posicao] = vooAssento;
        this.posicao++;
        return true;
    }

    @Override
    public boolean atualizar(VooAssento vooAssento, int idVooAssento) {
        if(idVooAssento >= this.posicao || idVooAssento < 0) {return false;}
        this.vooAssentos[this.posicao] = vooAssento;
        return true;
    }

    @Override
    public boolean deletar(int idVooAssento) {
        if(idVooAssento < 0 || idVooAssento >= this.posicao) {return false;} //Verifica se o id fornecido não está fora das posições disponíveis do vetor
        else if (idVooAssento == this.vooAssentos.length) { // Verifica se o id se refere a última posição disponível do vetor
            this.vooAssentos[idVooAssento] = null;
        }
        else { // Movendo conteúdos das posições do vetor a partir do id a ser deletado para a esquerda, substituindo a posição deletada
            for(int i = idVooAssento; i < --this.posicao; i++) { //a "posicao" sempre está um valor maior que a posição do vetor atual preenchida, por isso o "--"
                this.vooAssentos[i] = this.vooAssentos[i+1];
            }
            this.vooAssentos[--this.posicao] = null; // Apagando conteúdo da última posição, pois estará duplicado
        }
        // Delete realizado com sucesso para casos que caiam no "else if" ou "else"
        this.posicao--;
        return true;
    }

    @Override
    public VooAssento buscar(int idVooAssento) {
        if (idVooAssento < 0 || idVooAssento >= this.posicao) {return null;}
        return this.vooAssentos[idVooAssento];
    }

    @Override
    public VooAssento[] listar() {
        if(this.posicao == 0) {return null;} // se posição for 0, nenhum objeto foi incluído ainda no vetor
        return this.vooAssentos;
    }


}
