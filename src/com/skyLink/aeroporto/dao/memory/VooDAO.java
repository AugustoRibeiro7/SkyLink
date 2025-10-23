package com.skyLink.aeroporto.dao.memory;

import com.skyLink.aeroporto.dao.VooDAOInterface;
import com.skyLink.aeroporto.model.Voo;

public class VooDAO implements VooDAOInterface {
    //armazenamento de Voos
    private Voo[] voos;
    private int posicao;
    private

    //Construtor
    public VooDAO() {}

    @Override
    public void inserir(Voo voo) {
        voos = new Voo[1];
        voos[0] = voo;
    }

    public void atualizar(Voo voo) {}

    @Override
    public void deletar(Voo voo) {

    }

    @Override
    public Voo buscar(Voo voo){
        return null;
    }

    public Voo[] listar(){
        return null;
    }
}
