//package com.skyLink.aeroporto.dao.memory;
//
//import com.skyLink.aeroporto.dao.PassageiroDaoInterface;
//import com.skyLink.aeroporto.model.Passageiro;
//
//
//public class PassageiroDao implements PassageiroDaoInterface {
//
//    private Passageiro[] passageiros;
//    private int contador;
//
//    public PassageiroDao(int tamanho) {
//        passageiros = new Passageiro[tamanho];
//        contador = 0;
//    }
//
//    @Override
//    public void salvar(Passageiro passageiro) {
//        passageiros[contador++] = passageiro;
//    }
//
//    @Override
//    public Passageiro buscarPorId(int id) {
//        for (int i = 0; i < contador; i++) {
//            if (passageiros[i].getId() == id) {
//                return passageiros[i];
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public Passageiro[] listarTodos() {
//        return passageiros;
//    }
//
//    @Override
//    public void atualizar(Passageiro passageiro) {
//        for (int i = 0; i < contador; i++) {
//            if (passageiros[i].getId() == passageiro.getId()) {
//                passageiros[i] = passageiro;
//                return;
//            }
//        }
//    }
//
//    @Override
//    public void deletar(int id) {
//        for (int i = 0; i < contador; i++) {
//            if (passageiros[i].getId() == id) {
//                passageiros[i] = passageiros[contador - 1];
//                passageiros[contador - 1] = null;
//                contador--;
//                return;
//            }
//        }
//    }
//
//    // Método extra para login
//    public Passageiro buscarPorLogin(String login) {
//        for (int i = 0; i < contador; i++) {
//            if (passageiros[i].getLogin().equals(login)) {
//                return passageiros[i];
//            }
//        }
//        return null;
//    }
//}