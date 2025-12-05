package com.skyLink.aeroporto.dao.db;

import com.skyLink.aeroporto.dao.VooAssentoDaoInterface;
import com.skyLink.aeroporto.model.VooAssento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VooAssentoDaoMysql implements VooAssentoDaoInterface {

    @Override
    public boolean inserir(VooAssento assento) {
        return true; // O Trigger no banco já faz isso
    }

    // Busca apenas assentos que NÃO estão na tabela Ticket para aquele voo
    @Override
    public List<VooAssento> listarPorVoo(int vooId) {
        // Query mantida: traz assentos sem ticket
        String sql = """
            SELECT va.* FROM vooAssento va
            LEFT JOIN ticket t ON va.id = t.assentoId
            WHERE va.vooId = ? AND t.id IS NULL
            ORDER BY va.id
            """;
        List<VooAssento> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, vooId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    VooAssento a = new VooAssento();
                    a.setId(rs.getInt("id"));
                    a.setVooId(rs.getInt("vooId"));
                    a.setCodigoAssento(rs.getString("codigoAssento"));

                    // LENDO AS DATAS
                    var tsCriacao = rs.getTimestamp("dataCriacao");
                    var tsMod = rs.getTimestamp("dataModificacao");
                    if(tsCriacao != null) a.setDataCriacao(tsCriacao.toLocalDateTime());
                    if(tsMod != null) a.setDataModificacao(tsMod.toLocalDateTime());

                    lista.add(a);
                }
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return lista;
    }

    @Override
    public VooAssento buscarPorId(int id) {
        String sql = "SELECT * FROM vooAssento WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    VooAssento a = new VooAssento();
                    a.setId(rs.getInt("id"));
                    a.setVooId(rs.getInt("vooId"));
                    a.setCodigoAssento(rs.getString("codigoAssento"));
                    return a;
                }
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return null;
    }

    @Override public boolean atualizar(VooAssento assento) { return false; }
    @Override public boolean deletarPorVoo(int vooId) { return false; }
}