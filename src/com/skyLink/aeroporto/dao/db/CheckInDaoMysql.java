package com.skyLink.aeroporto.dao.db;

import com.skyLink.aeroporto.model.CheckIn;
import com.skyLink.aeroporto.model.Passageiro;
import com.skyLink.aeroporto.model.Ticket;
import com.skyLink.aeroporto.model.Voo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CheckInDaoMysql {
    public boolean inserir(CheckIn checkIn) {
        String sql = "INSERT INTO checkin (ticketId, dataCheckIn) VALUES (?, NOW())";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, checkIn.getTicket().getId());

            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) checkIn.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) { throw new RuntimeException("Erro ao inserir Check-in.", e); }
        return false;
    }

    public List<CheckIn> listarTodosDetelhado() {
        List<CheckIn> lista = new ArrayList<>();
        // JOIN triplo: CheckIn -> Ticket -> Passageiro & Voo
        String sql = """
        SELECT c.id, c.dataCheckIn, 
               t.id AS ticketId, t.codigo AS ticketCodigo, 
               p.id AS passId, p.nome AS passNome, 
               v.id AS vooId, v.dataVoo
        FROM checkin c
        JOIN ticket t ON c.ticketId = t.id
        JOIN passageiro p ON t.passageiroId = p.id
        JOIN voo v ON t.vooId = v.id
        ORDER BY c.dataCheckIn DESC
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CheckIn c = new CheckIn();
                c.setId(rs.getInt("id"));

                // Tratamento da data do Check-in
                java.sql.Timestamp tsCheck = rs.getTimestamp("dataCheckIn");
                if (tsCheck != null) c.setDataCheckIn(tsCheck.toLocalDateTime());

                // Montando o Ticket (Parcial, só o necessário para visualização)
                Ticket t = new Ticket();
                t.setId(rs.getInt("ticketId"));
                t.setCodigo(rs.getString("ticketCodigo"));

                // Montando o Passageiro dentro do Ticket
                Passageiro p = new Passageiro();
                p.setId(rs.getInt("passId"));
                p.setNome(rs.getString("passNome"));
                t.setPassageiro(p);

                // Montando o Voo dentro do Ticket
                Voo v = new Voo();
                v.setId(rs.getInt("vooId"));
                java.sql.Timestamp tsVoo = rs.getTimestamp("dataVoo");
                if (tsVoo != null) v.setDataVoo(tsVoo.toLocalDateTime());
                t.setVoo(v);

                c.setTicket(t); // Vincula ticket ao check-in
                lista.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos os check-ins.", e);
        }
        return lista;
    }
}