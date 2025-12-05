//package com.skyLink.aeroporto.dao.memory;
//
//import com.skyLink.aeroporto.dao.AeroportoDaoInterface;
//import com.skyLink.aeroporto.model.Aeroporto;
//
//public class AeroportoDao implements AeroportoDaoInterface {
//    private Aeroporto[] aeroportos;
//    private int contador;
//
//    public AeroportoDao(int tamanho) {
//        aeroportos = new Aeroporto[tamanho];
//        contador = 0;
//    }
//
//    @Override
//    public void salvar(Aeroporto aeroporto) {
//        aeroportos[contador++] = aeroporto;
//    }
//
//    @Override
//    public Aeroporto buscarPorId(int id) {
//        for (int i = 0; i < contador; i++) {
//            if (aeroportos[i].getId() == id) {
//                return aeroportos[i];
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public Aeroporto[] listarTodos() {
//        return aeroportos;
//    }
//
//    @Override
//    public void atualizar(Aeroporto aeroporto) {
//        for (int i = 0; i < contador; i++) {
//            if (aeroportos[i].getId() == aeroporto.getId()) {
//                aeroportos[i] = aeroporto;
//                return;
//            }
//        }
//    }
//
//    @Override
//    public void deletar(int id) {
//        for (int i = 0; i < contador; i++) {
//            if (aeroportos[i].getId() == id) {
//                aeroportos[i] = aeroportos[contador - 1];
//                aeroportos[contador - 1] = null;
//                contador--;
//                return;
//            }
//        }
//    }
//}
