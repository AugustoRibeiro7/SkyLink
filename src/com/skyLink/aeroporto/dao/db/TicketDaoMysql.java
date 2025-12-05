package com.skyLink.aeroporto.dao.db;

import com.skyLink.aeroporto.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TicketDaoMysql {

    public boolean inserir(Ticket ticket) {
        String sql = """
            INSERT INTO ticket (valor, vooId, passageiroId, assentoId, codigo, dataCriacao) 
            VALUES (?, ?, ?, ?, ?, NOW())
            """;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Gera código aleatório de 5 caracteres
            String codigo = UUID.randomUUID().toString().substring(0, 5).toUpperCase();
            ticket.setCodigo(codigo);

            ps.setDouble(1, ticket.getValor());
            ps.setInt(2, ticket.getVoo().getId());
            ps.setInt(3, ticket.getPassageiro().getId());
            ps.setInt(4, ticket.getAssento().getId());
            ps.setString(5, codigo);

            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) ticket.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) { throw new RuntimeException("Erro ao comprar ticket (assento pode estar ocupado).", e); }
        return false;
    }

    // Método CRUCIAL para o Check-in: Buscar Ticket e trazer o Voo junto (JOIN)
    public Ticket buscarPorCodigo(String codigo) {
        String sql = """
            SELECT t.*, v.dataVoo, v.origemId, v.destinoId 
            FROM ticket t
            JOIN voo v ON t.vooId = v.id
            WHERE t.codigo = ?
            """;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Ticket t = new Ticket();
                    t.setId(rs.getInt("id"));
                    t.setCodigo(rs.getString("codigo"));
                    t.setValor(rs.getDouble("valor"));

                    // LENDO AS DATAS
                    var tsCriacao = rs.getTimestamp("dataCriacao");
                    var tsMod = rs.getTimestamp("dataModificacao");
                    if(tsCriacao != null) t.setDataCriacao(tsCriacao.toLocalDateTime());
                    if(tsMod != null) t.setDataModificacao(tsMod.toLocalDateTime());

                    // Monta Voo (parcial)
                    Voo v = new Voo();
                    v.setId(rs.getInt("vooId"));
                    v.setDataVoo(rs.getTimestamp("dataVoo").toLocalDateTime());
                    t.setVoo(v);

                    return t;
                }
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return null;
    }

    // Lista tickets de um passageiro (para menu do cliente)
    public List<Ticket> listarPorPassageiro(int passageiroId) {
        List<Ticket> lista = new ArrayList<>();
        String sql = "SELECT * FROM ticket WHERE passageiroId = ? ORDER BY dataCriacao DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, passageiroId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ticket t = new Ticket();
                    t.setId(rs.getInt("id"));
                    t.setCodigo(rs.getString("codigo"));
                    t.setValor(rs.getDouble("valor"));

                    // Populando datas
                    var tsCriacao = rs.getTimestamp("dataCriacao");
                    var tsMod = rs.getTimestamp("dataModificacao");
                    if(tsCriacao != null) t.setDataCriacao(tsCriacao.toLocalDateTime());
                    if(tsMod != null) t.setDataModificacao(tsMod.toLocalDateTime());

                    // Populando Voo (Apenas ID)
                    Voo v = new Voo();
                    v.setId(rs.getInt("vooId"));
                    t.setVoo(v);

                    // Populando Assento (Apenas ID)
                    VooAssento a = new VooAssento();
                    a.setId(rs.getInt("assentoId"));
                    t.setAssento(a);

                    // Como já temos o ID do passageiro, podemos setar o objeto básico
                    Passageiro p = new Passageiro();
                    p.setId(passageiroId);
                    t.setPassageiro(p);

                    lista.add(t);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar tickets do passageiro.", e);
        }
        return lista;
    }
}