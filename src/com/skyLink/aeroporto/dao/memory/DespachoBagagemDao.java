package com.skyLink.aeroporto.dao.memory;

import com.skyLink.aeroporto.dao.DespachoBagagemDaoInterface;
import com.skyLink.aeroporto.model.DespachoBagagem;

public class DespachoBagagemDao implements DespachoBagagemDaoInterface {
    DespachoBagagem[] despachosBagagem;
    int posicao; //sempre estará um número maior do que a última posição do vetor com um objeto

    //Construtor
    public DespachoBagagemDao() { // Inicializando vetor e posicao
        this.despachosBagagem = new DespachoBagagem[10];
        this.posicao = 0;
    }

    @Override
    public boolean inserir(DespachoBagagem despachoBagagem) {
        if(this.posicao > this.despachosBagagem.length) {return false;}
        this.despachosBagagem[this.posicao] = despachoBagagem;
        this.posicao++;
        return true;
    }

    @Override
    public boolean atualizar(DespachoBagagem despachoBagagem, int idDespachoBagagem) {
        if(idDespachoBagagem >= this.posicao || idDespachoBagagem < 0) {return false;}
        this.despachosBagagem[this.posicao] = despachoBagagem;
        return true;
    }

    @Override
    public boolean deletar(int idDespachoBagagem) {
        if(idDespachoBagagem < 0 || idDespachoBagagem >= this.posicao) {return false;} //Verifica se o id fornecido não está fora das posições disponíveis do vetor
        else if (idDespachoBagagem == this.despachosBagagem.length) { // Verifica se o id se refere a última posição disponível do vetor
            this.despachosBagagem[idDespachoBagagem] = null;
        }
        else { // Movendo conteúdos das posições do vetor a partir do id a ser deletado para a esquerda, substituindo a posição deletada
            for(int i = idDespachoBagagem; i < --this.posicao; i++) { //a "posicao" sempre está um valor maior que a posição do vetor atual preenchida, por isso o "--"
                this.despachosBagagem[i] = this.despachosBagagem[i+1];
            }
            this.despachosBagagem[--this.posicao] = null; // Apagando conteúdo da última posição, pois estará duplicado
        }
        // Delete realizado com sucesso para casos que caiam no "else if" ou "else"
        this.posicao--;
        return true;
    }

    @Override
    public DespachoBagagem buscar(int idDespachoBagagem) {
        if (idDespachoBagagem < 0 || idDespachoBagagem >= this.posicao) {return null;}
        return this.despachosBagagem[idDespachoBagagem];
    }

    @Override
    public DespachoBagagem[] listar() {
        if(this.posicao == 0) {return null;} // se posição for 0, nenhum objeto foi incluído ainda no vetor
        return this.despachosBagagem;
    }
}
