package com.skyLink.aeroporto;

import com.skyLink.aeroporto.controller.*;
import com.skyLink.aeroporto.dao.db.*;
import com.skyLink.aeroporto.service.*;
import com.skyLink.aeroporto.view.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. DAOs
        var passageiroDao = new PassageiroDaoMysql();
        var aeroportoDao = new AeroportoDaoMysql();
        var companhiaDao = new CompanhiaAereaDaoMysql();
        var vooDao = new VooDaoMysql();
        var ticketDao = new TicketDaoMysql();
        var checkInDao = new CheckInDaoMysql();
        var cartaoEmbarqueDao = new CartaoEmbarqueDaoMysql();
        var vooAssentoDao = new VooAssentoDaoMysql();

        // 2. Services
        var loginService = new LoginService(passageiroDao);
        var passageiroService = new PassageiroService(passageiroDao);
        var aeroportoService = new AeroportoService(aeroportoDao);
        var companhiaService = new CompanhiaAereaService(companhiaDao);
        var vooService = new VooService(vooDao);
        var vooAssentoService = new VooAssentoService(vooAssentoDao);
        var ticketService = new TicketService(ticketDao, vooAssentoService);
        var checkInService = new CheckInService(checkInDao, cartaoEmbarqueDao);

        // 3. Controllers
        var loginController = new LoginController(loginService);
        var passageiroController = new PassageiroController(passageiroService);
        var aeroportoController = new AeroportoController(aeroportoService);
        var companhiaController = new CompanhiaAereaController(companhiaService);
        var vooController = new VooController(vooService, companhiaService);
        var ticketController = new TicketController(ticketService, vooService, vooAssentoService);
        var checkInController = new CheckInController(checkInService, ticketService);

        // 4. Views
        var loginView = new LoginView(loginController);
        var passageiroView = new PassageiroView(passageiroController,scanner);
        var aeroportoView = new AeroportoView(aeroportoController, scanner);
        var vooView = new VooView(vooController, aeroportoController, companhiaController);
        var companhiaView = new CompanhiaAereaView(companhiaController);
        var ticketView = new TicketView(ticketController, scanner);
        var checkInView = new CheckInView(checkInController, scanner);

        // 5. Menu Principal
        var menuPrincipal = new MenuPrincipalView(
                loginView,
                vooView,
                companhiaView,
                ticketView,
                checkInView,
                passageiroView,
                aeroportoView,
                scanner
        );

        menuPrincipal.exibirMenu();
        scanner.close();
    }
}