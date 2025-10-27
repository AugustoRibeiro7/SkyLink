package com.skyLink.aeroporto;

import com.skyLink.aeroporto.controller.*;

import com.skyLink.aeroporto.dao.memory.PassageiroDao;
import com.skyLink.aeroporto.dao.memory.AeroportoDao;
import com.skyLink.aeroporto.dao.memory.CompanhiaAereaDao;

import com.skyLink.aeroporto.model.Aeroporto;
import com.skyLink.aeroporto.model.Passageiro;
import com.skyLink.aeroporto.service.LoginService;
import com.skyLink.aeroporto.view.LoginView;
import com.skyLink.aeroporto.view.MenuPrincipalView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Cria DAOs em memória (tamanho fixo)
        PassageiroDao passageiroDao = new PassageiroDao(20);
        AeroportoDao aeroportoDao = new AeroportoDao(20);
        CompanhiaAereaDao companhiaAereaDao = new CompanhiaAereaDao(20);

        // Popular um passageiro
        Passageiro pTeste = new Passageiro(
                1,
                "Ranieri",
                LocalDate.of(2002, 12, 10),
                "123.456.789-00",
                "ranieri",
                "senha123",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        passageiroDao.salvar(pTeste);

        // Popular um aeroporto
        Aeroporto aTeste = new Aeroporto(
                1,
                "Guarulhos",
                "Guarulhos",
                "GRU",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        aeroportoDao.salvar(aTeste);

        // Cria controllers (passando os DAOs)
        PassageiroController passageiroController = new PassageiroController(passageiroDao);
        AeroportoController aeroportoController = new AeroportoController(aeroportoDao);
        CompanhiaAereaController companhiaAereaController = new CompanhiaAereaController(companhiaAereaDao);
        VooController vooController = new VooController(companhiaAereaDao); // Sem VooDao, pois é instanciado no VooController
        TicketController ticketController = new TicketController(vooController, passageiroDao);

        // Login service e controller
        LoginService loginService = new LoginService(passageiroDao);
        LoginController loginController = new LoginController(loginService);

        // Criando o loginView
        LoginView loginView = new LoginView(loginController);

        // Menu principal (passando os controllers)
        MenuPrincipalView menu = new MenuPrincipalView(
                loginView,
                passageiroController,
                aeroportoController,
                companhiaAereaController,
                vooController,
                ticketController,
                scanner
        );

        // Executa o menu
        menu.exibirMenu();

        System.out.println("Aplicação encerrada.");
    }
}
