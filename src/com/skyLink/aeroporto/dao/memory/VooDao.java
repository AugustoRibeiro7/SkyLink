package com.skyLink.aeroporto.dao.memory;

import com.skyLink.aeroporto.dao.VooDaoInterface;
import com.skyLink.aeroporto.model.Voo;

public class VooDao implements VooDaoInterface {
    //armazenamento de Voos
    private Voo[] voos;
    private int posicao;

    //Construtor
    public VooDao() { // Inicializando vetor e posicao
        this.voos = new Voo[10];
        this.posicao = 0;
    }

    @Override
    public boolean inserir(Voo voo) {
        if(this.posicao > this.voos.length) {return false;}
        this.voos[this.posicao] = voo;
        this.posicao++;
        return true;
    }

    @Override
    public boolean atualizar(Voo voo, int idVoo) {
        if(idVoo >= this.posicao || idVoo < 0) {return false;}
        this.voos[idVoo] = voo;
        return true;
    }

    @Override
    public boolean deletar(int idVoo) {
        if(idVoo >= this.posicao || idVoo < 0) {return false;} //Verifica se o id fornecido não está fora das posições disponíveis do vetor
        else if(idVoo == this.voos.length) { // Verifica se o id se refere a última posição disponível do vetor
            this.voos[idVoo] = null;
        }
        else { // Movendo conteúdos das posições do vetor a partir do id a ser deletado para a esquerda, substituindo a posição deletada
            for(int i = idVoo; i < --this.posicao; i++) { //a "posicao" sempre está um valor maior que a posição do vetor atual preenchida, por isso o "--"
                this.voos[i] = this.voos[i+1];
            }
            this.voos[--this.posicao] = null; // Apagando conteúdo da última posição, pois estará duplicado
        }
        // Delete realizado com sucesso para casos que caiam no "else if" ou "else"
        this.posicao--;
        return true;
    }

    @Override
    public Voo buscar(int idVoo){
        if(idVoo >= this.voos.length || idVoo < 0) {return null;}
        return this.voos[idVoo];
    }

    @Override
    public Voo[] listar(){
        if(this.posicao == 0) {return null;} // se posição for 0, nenhum objeto foi incluído ainda no vetor
        return this.voos;
    }
}
