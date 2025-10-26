package com.skyLink.aeroporto.dao;

import com.skyLink.aeroporto.model.CheckIn;

public interface CheckInDaoInterface {
    abstract boolean inserir(CheckIn checkIn);
    abstract boolean atualizar(CheckIn checkIn, int identificador);
    abstract boolean deletar(int idCheckIn);
    abstract CheckIn buscar(int idCheckIn);
    abstract CheckIn[] listar();
}
