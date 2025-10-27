package com.skyLink.aeroporto.dao.memory;

import com.skyLink.aeroporto.dao.VooDaoInterface;
import com.skyLink.aeroporto.model.Voo;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class VooDao implements VooDaoInterface {
    //armazenamento de Voos
    private Voo[] voos;
    private int tamanho;

    //Construtor
    public VooDao() { // Inicializando vetor e posicao
        this.voos = new Voo[10];
        this.tamanho = 0;
    }

    @Override
    public boolean inserir(Voo voo) {
        if (voo == null) {
            throw new IllegalArgumentException("Voo não pode ser nulo");
        }
        if(this.tamanho > this.voos.length) {return false;}
        this.voos[this.tamanho] = voo;
        this.tamanho++;
        voo.setId(tamanho); // Define o ID como tamanho (baseado em 1)
        return true;
    }

    @Override
    public boolean atualizar(Voo voo, int idVoo) {
        if(idVoo > this.tamanho || idVoo <= 0) {return false;}
        this.voos[idVoo-1] = voo;
        voo.setId(idVoo); // Garante que o ID do voo seja consistente
        return true;
    }

    @Override
    public boolean deletar(int idVoo) {
        int posicaoId = idVoo -1; // Posição do id no vetor, estamos usando um identificador baseado em: 1,2,3. Por isso o decremento para encontrar a posição
        if(idVoo > this.tamanho || idVoo < 0) {return false;} //Verifica se o id fornecido não está fora das posições disponíveis do vetor
        else if(idVoo == this.voos.length) { // Verifica se o id se refere a última posição disponível do vetor
            this.voos[posicaoId] = null;
        }
        else { // Movendo conteúdos das posições do vetor a partir do id a ser deletado para a esquerda, substituindo a posição deletada
            for(int i = posicaoId; i < this.tamanho; i++) { //o tamanho sempre está um valor maior que a posição do vetor atual preenchida, por isso o "-1"
                this.voos[i] = this.voos[i+1];
            }
            this.voos[this.tamanho-1] = null; // Apagando conteúdo da última posição, pois estará duplicado
        }
        // Delete realizado com sucesso para casos que caiam no "else if" ou "else"
        this.tamanho--;
        return true;
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
