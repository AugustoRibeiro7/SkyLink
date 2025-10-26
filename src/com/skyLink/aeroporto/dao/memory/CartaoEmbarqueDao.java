package com.skyLink.aeroporto.dao.memory;

import com.skyLink.aeroporto.dao.CartaoEmbarqueDaoInterface;
import com.skyLink.aeroporto.model.CartaoEmbarque;

public class CartaoEmbarqueDao implements CartaoEmbarqueDaoInterface {
    CartaoEmbarque[] cartoesEmbarque;
    int posicao; //sempre estará um número maior do que a última posição do vetor com um objeto

    //Construtor
    public  CartaoEmbarqueDao() {
        this.cartoesEmbarque = new CartaoEmbarque[10];
        this.posicao = 0;
    }

    @Override
    public boolean inserir(CartaoEmbarque cartaoEmbarque) {
        if(this.posicao > this.cartoesEmbarque.length) {return false;}
        this.cartoesEmbarque[this.posicao] = cartaoEmbarque;
        this.posicao++;
        return true;
    }

    @Override
    public boolean atualizar(CartaoEmbarque cartaoEmbarque, int idCartaoEmbarque) {
        if(idCartaoEmbarque >= this.posicao || idCartaoEmbarque < 0) {return false;}
        this.cartoesEmbarque[this.posicao] = cartaoEmbarque;
        return true;
    }

    @Override
    public boolean deletar(int idCartaoEmbarque) {
        if(idCartaoEmbarque < 0 || idCartaoEmbarque >= this.posicao) {return false;} //Verifica se o id fornecido não está fora das posições disponíveis do vetor
        else if (idCartaoEmbarque == this.cartoesEmbarque.length) { // Verifica se o id se refere a última posição disponível do vetor
            this.cartoesEmbarque[idCartaoEmbarque] = null;
        }
        else { // Movendo conteúdos das posições do vetor a partir do id a ser deletado para a esquerda, substituindo a posição deletada
            for(int i = idCartaoEmbarque; i < --this.posicao; i++) { //a "posicao" sempre está um valor maior que a posição do vetor atual preenchida, por isso o "--"
                this.cartoesEmbarque[i] = this.cartoesEmbarque[i+1];
            }
            this.cartoesEmbarque[--this.posicao] = null; // Apagando conteúdo da última posição, pois estará duplicado
        }
        // Delete realizado com sucesso para casos que caiam no "else if" ou "else"
        this.posicao--;
        return true;
    }

    @Override
    public CartaoEmbarque buscar(int idCartaoEmbarque) {
        if (idCartaoEmbarque < 0 || idCartaoEmbarque >= this.posicao) {return null;}
        return this.cartoesEmbarque[idCartaoEmbarque];
    }

    @Override
    public CartaoEmbarque[] listar() {
        if(this.posicao == 0) {return null;} // se posição for 0, nenhum objeto foi incluído ainda no vetor
        return this.cartoesEmbarque;
    }
}
