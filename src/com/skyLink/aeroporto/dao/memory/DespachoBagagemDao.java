//package com.skyLink.aeroporto.dao.memory;
//
//import com.skyLink.aeroporto.dao.DespachoBagagemDaoInterface;
//import com.skyLink.aeroporto.model.DespachoBagagem;
//import java.time.LocalDateTime;
//
//public class DespachoBagagemDao implements DespachoBagagemDaoInterface {
//    private DespachoBagagem[] despachos;
//    private int tamanho;
//    private int capacidade;
//    private int contId;
//
//    public DespachoBagagemDao(int capacidade) {
//        this.capacidade = capacidade;
//        this.despachos = new DespachoBagagem[this.capacidade];
//        this.tamanho = 0;
//        this.contId = 0;
//    }
//
//    @Override
//    public boolean inserir(DespachoBagagem despacho) {
//        if (this.tamanho >= this.capacidade) {
//            return false;
//        }
//        despacho.setId(this.contId + 1);
//        despacho.setDataCriacao(LocalDateTime.now());
//        despacho.setDataModificacao(despacho.getDataCriacao());
//        this.despachos[this.tamanho++] = despacho;
//        return true;
//    }
//
//    @Override
//    public boolean atualizar(DespachoBagagem despacho, int identificador) {
//        for (int i = 0; i < this.tamanho; i++) {
//            if (this.despachos[i] != null && this.despachos[i].getId() == identificador) {
//                despacho.setId(identificador);
//                despacho.setDataModificacao(LocalDateTime.now());
//                this.despachos[i] = despacho;
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public boolean deletar(int id) {
//        for (int i = 0; i < this.tamanho; i++) {
//            if (this.despachos[i] != null && this.despachos[i].getId() == id) {
//                this.despachos[i] = null;
//                for (int j = i; j < this.tamanho - 1; j++) {
//                    this.despachos[j] = this.despachos[j + 1];
//                }
//                this.despachos[this.tamanho - 1] = null;
//                this.tamanho--;
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public DespachoBagagem buscar(int id) {
//        for (int i = 0; i < this.tamanho; i++) {
//            if (this.despachos[i] != null && this.despachos[i].getId() == id) {
//                return this.despachos[i];
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public DespachoBagagem[] listar() {
//        DespachoBagagem[] resultado = new DespachoBagagem[this.tamanho];
//        for (int i = 0; i < this.tamanho; i++) {
//            resultado[i] = this.despachos[i];
//        }
//        return resultado;
//    }
//
//    @Override
//    public DespachoBagagem[] listarPorTicket(int idTicket) {
//        int count = 0;
//        for (int i = 0; i < this.tamanho; i++) {
//            if (this.despachos[i] != null && this.despachos[i].getTicket().getId() == idTicket) {
//                count++;
//            }
//        }
//
//        DespachoBagagem[] resultado = new DespachoBagagem[count];
//        int index = 0;
//        for (int i = 0; i < this.tamanho; i++) {
//            if (this.despachos[i] != null && this.despachos[i].getTicket().getId() == idTicket) {
//                resultado[index] = this.despachos[i];
//                index++;
//            }
//        }
//        return resultado;
//    }
//}