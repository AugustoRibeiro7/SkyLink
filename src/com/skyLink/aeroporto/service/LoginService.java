package com.skyLink.aeroporto.service;

import com.skyLink.aeroporto.dao.PassageiroDaoInterface;
import com.skyLink.aeroporto.dao.memory.PassageiroDao;
import com.skyLink.aeroporto.model.Passageiro;

public class LoginService {

    private PassageiroDaoInterface passageiroDao;

    //Construtor
    public LoginService(PassageiroDaoInterface passageiroDao) {
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