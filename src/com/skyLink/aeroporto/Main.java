package com.skyLink.aeroporto;

import com.skyLink.aeroporto.controller.LoginController;
import com.skyLink.aeroporto.controller.PassageiroController;
import com.skyLink.aeroporto.controller.AeroportoController;
import com.skyLink.aeroporto.controller.CompanhiaAereaController;

import com.skyLink.aeroporto.dao.memory.PassageiroDao;
import com.skyLink.aeroporto.dao.memory.AeroportoDao;
import com.skyLink.aeroporto.dao.memory.CompanhiaAereaDao;

import com.skyLink.aeroporto.model.Passageiro;
import com.skyLink.aeroporto.service.LoginService;
import com.skyLink.aeroporto.view.MenuPrincipalView;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        // Cria DAOs em memória (tamanho fixo)
        PassageiroDao passageiroDao = new PassageiroDao(20);
        AeroportoDao aeroportoDao = new AeroportoDao(20);
        CompanhiaAereaDao companhiaAereaDao = new CompanhiaAereaDao(20);

        // Popula um passageiro de teste (para logar sem precisar cadastrar)
        Passageiro pTeste = new Passageiro(
                1,
                "Ranieri",
                LocalDate.of(2002, 12, 10),
                "123.456.789-00",
                "ranieri",       // login
                "senha123",      // senha
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        passageiroDao.salvar(pTeste);

        // Cria controllers (passando os DAOs)
        PassageiroController passageiroController = new PassageiroController(passageiroDao);
        AeroportoController aeroportoController = new AeroportoController(aeroportoDao);
        CompanhiaAereaController companhiaAereaController = new CompanhiaAereaController(companhiaAereaDao);

        // Login service e controller
        LoginService loginService = new LoginService(passageiroDao);
        LoginController loginController = new LoginController(loginService);

        // Menu principal (passando os controllers)
        MenuPrincipalView menu = new MenuPrincipalView(
                loginController,
                passageiroController,
                aeroportoController,
                companhiaAereaController
        );

        // Executa o menu
        menu.exibirMenu();

        System.out.println("Aplicação encerrada.");
    }
}
