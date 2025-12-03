package com.skyLink.aeroporto.service;

import com.skyLink.aeroporto.dao.CheckInDaoInterface;
import com.skyLink.aeroporto.dao.CartaoEmbarqueDaoInterface;
import com.skyLink.aeroporto.model.CheckIn;
import com.skyLink.aeroporto.model.CartaoEmbarque;
import com.skyLink.aeroporto.model.Passageiro;
import com.skyLink.aeroporto.model.Ticket;
import com.skyLink.aeroporto.model.Voo;
import com.skyLink.aeroporto.model.VooAssento;

import java.time.LocalDateTime;

public class CheckInService {
    private final CheckInDaoInterface checkInDao;
    private final CartaoEmbarqueDaoInterface cartaoEmbarqueDao;
    private final VooAssentoService vooAssentoService;

    //Construtor
    public CheckInService(CheckInDaoInterface checkInDao, CartaoEmbarqueDaoInterface cartaoEmbarqueDao, VooAssentoService vooAssentoService) {
        this.checkInDao = checkInDao;
        this.cartaoEmbarqueDao = cartaoEmbarqueDao;
        this.vooAssentoService = vooAssentoService;
    }

    public CartaoEmbarque realizarCheckIn(Ticket ticket, Passageiro passageiro, Voo voo, int codAssento) {
        if (ticket == null || passageiro == null || voo == null) {
            throw new IllegalArgumentException("Ticket, passageiro e voo não podem ser nulos.");
        }
        if (ticket.getPassageiro().getId() != passageiro.getId() || ticket.getVoo().getId() != voo.getId()) {
            throw new IllegalArgumentException("Ticket não corresponde ao passageiro ou voo fornecido.");
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime limiteCheckIn = voo.getDataVoo().minusHours(24);
        if (now.isBefore(limiteCheckIn)) {
            throw new IllegalArgumentException("Check-in só pode ser feito 24 horas antes do voo.");
        }
        // Criar assento
        if (!this.vooAssentoService.adicionarAssento(codAssento, voo.getId(), passageiro.getId())) {
            throw new IllegalStateException("Falha ao criar assento para o passageiro.");
        }
        VooAssento vooAssento = this.vooAssentoService.buscarPorVooEPassageiro(voo.getId(), passageiro.getId());
        // Criar check-in
        CheckIn checkIn = new CheckIn(0, ticket, passageiro);
        if (!this.checkInDao.inserir(checkIn)) {
            throw new IllegalStateException("Falha ao inserir check-in.");
        }
        // Gerar cartão de embarque
        CartaoEmbarque cartao = new CartaoEmbarque(0, passageiro, voo, ticket, vooAssento);
        if (!this.cartaoEmbarqueDao.inserir(cartao)) {
            throw new IllegalStateException("Falha ao inserir cartão de embarque.");
        }
        return cartao;
    }

    public CheckIn[] listar() {
        return this.checkInDao.listar();
    }

    public CartaoEmbarque[] listarCartoes() {
        return this.cartaoEmbarqueDao.listar();
    }
}