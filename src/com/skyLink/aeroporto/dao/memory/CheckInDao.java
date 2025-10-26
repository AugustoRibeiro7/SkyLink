package com.skyLink.aeroporto.dao.memory;

import com.skyLink.aeroporto.dao.CheckInDaoInterface;
import com.skyLink.aeroporto.model.CheckIn;

public class CheckInDao implements CheckInDaoInterface {
    CheckIn[] checkIns;
    int posicao;

    //Construtor
    public CheckInDao() { // Inicializando vetor e posicao
        this.checkIns = new CheckIn[10];
        this.posicao = 0;
    }

    @Override
    public boolean inserir(CheckIn checkIn) {
        if(this.posicao > this.checkIns.length) {return false;}
        this.checkIns[this.posicao] = checkIn;
        this.posicao++;
        return true;
    }

    @Override
    public boolean atualizar(CheckIn checkIn, int idCheckIn) {
        if(idCheckIn >= this.posicao || idCheckIn < 0) {return false;}
        this.checkIns[this.posicao] = checkIn;
        return true;
    }

    @Override
    public boolean deletar(int idCheckIn) {
        if(idCheckIn < 0 || idCheckIn >= this.posicao) {return false;} //Verifica se o id fornecido não está fora das posições disponíveis do vetor
        else if (idCheckIn == this.checkIns.length) { // Verifica se o id se refere a última posição disponível do vetor
            this.checkIns[idCheckIn] = null;
        }
        else { // Movendo conteúdos das posições do vetor a partir do id a ser deletado para a esquerda, substituindo a posição deletada
            for(int i = idCheckIn; i < --this.posicao; i++) { //a "posicao" sempre está um valor maior que a posição do vetor atual preenchida, por isso o "--"
                this.checkIns[i] = this.checkIns[i+1];
            }
            this.checkIns[--this.posicao] = null; // Apagando conteúdo da última posição, pois estará duplicado
        }
        // Delete realizado com sucesso para casos que caiam no "else if" ou "else"
        this.posicao--;
        return true;
    }

    @Override
    public CheckIn buscar(int idCheckIn) {
        if (idCheckIn < 0 || idCheckIn >= this.posicao) {return null;}
        return this.checkIns[idCheckIn];
    }

    @Override
    public CheckIn[] listar() {
        if(this.posicao == 0) {return null;} // se posição for 0, nenhum objeto foi incluído ainda no vetor
        return this.checkIns;
    }
}
