package com.skyLink.aeroporto.service;

import com.skyLink.aeroporto.dao.db.CartaoEmbarqueDaoMysql;
import com.skyLink.aeroporto.dao.db.CheckInDaoMysql;
import com.skyLink.aeroporto.model.CartaoEmbarque;
import com.skyLink.aeroporto.model.CheckIn;
import com.skyLink.aeroporto.model.Passageiro;
import com.skyLink.aeroporto.model.Ticket;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CheckInService {
    private final CheckInDaoMysql checkInDao;
    private final CartaoEmbarqueDaoMysql cartaoDao;

    public CheckInService(CheckInDaoMysql checkInDao, CartaoEmbarqueDaoMysql cartaoDao) {
        this.checkInDao = checkInDao;
        this.cartaoDao = cartaoDao;
    }

    public CartaoEmbarque realizarCheckIn(Ticket ticket) {
        // 1. Regra de Negócio: Validação de data e hora
        LocalDateTime agora = LocalDateTime.now();

        // Garante que a data do voo foi carregada (evita NullPointerException)
        if (ticket.getVoo() == null || ticket.getVoo().getDataVoo() == null) {
            throw new IllegalArgumentException("Dados do voo não foram carregados corretamente.");
        }

        LocalDateTime dataVoo = ticket.getVoo().getDataVoo();

        // Se o voo já partiu
        if (agora.isAfter(dataVoo)) {
            throw new IllegalArgumentException("Voo já partiu. Check-in encerrado.");
        }

        // Se faltam mais de 24h
        long horasRestantes = ChronoUnit.HOURS.between(agora, dataVoo);
        if (horasRestantes > 24) {
            throw new IllegalArgumentException("Check-in disponível apenas 24h antes do voo. Faltam: " + horasRestantes + " horas.");
        }

        // 2. Persistir Check-in
        CheckIn checkIn = new CheckIn();
        checkIn.setTicket(ticket);
        checkIn.setDataCheckIn(agora);

        // Tenta salvar no banco
        if (!checkInDao.inserir(checkIn)) {
            // Se falhar, pode ser que já exista um check-in para este ticket (chave única no banco)
            throw new RuntimeException("Erro ao registrar check-in ou check-in já realizado.");
        }

        // 3. Gerar Cartão de Embarque automaticamente
        CartaoEmbarque cartao = new CartaoEmbarque();
        cartao.setCheckIn(checkIn);
        cartao.setPortao(gerarPortaoAleatorio());
        cartao.setDataEmissao(agora);

        cartaoDao.inserir(cartao);

        return cartao;
    }

    public List<CheckIn> listarTodos() {
        return checkInDao.listarTodosDetelhado();
    }

    public List<CartaoEmbarque> listarCartoesPorPassageiro(Passageiro passageiro) {
        return cartaoDao.listarPorPassageiro(passageiro.getId());
    }

    private String gerarPortaoAleatorio() {
        // Gera um portão entre G1 e G20
        int numero = (int) (Math.random() * 20) + 1;
        return "G" + numero;
    }
}