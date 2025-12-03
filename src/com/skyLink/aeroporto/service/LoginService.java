package com.skyLink.aeroporto.service;

import com.skyLink.aeroporto.dao.memory.PassageiroDao;
import com.skyLink.aeroporto.model.Passageiro;

public class LoginService {

    private PassageiroDao passageiroDao;

    //Construtor
    public LoginService(PassageiroDao passageiroDao) {
        this.passageiroDao = passageiroDao;
    }

    public Passageiro autenticar(String login, String senha) {
        Passageiro passageiro = passageiroDao.buscarPorLogin(login);
        if (passageiro != null && passageiro.getSenha().equals(senha)) {
            return passageiro;
        }
        return null;
    }
}