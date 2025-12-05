package com.skyLink.aeroporto.dao.db;

import com.skyLink.aeroporto.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartaoEmbarqueDaoMysql {

    public boolean inserir(CartaoEmbarque cartao) {
        String sql = "INSERT INTO cartao_embarque (checkInId, portao, dataEmissao) VALUES (?, ?, NOW())";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, cartao.getCheckIn().getId());
            ps.setString(2, cartao.getPortao());

            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) { throw new RuntimeException("Erro ao gerar Cartão de Embarque.", e); }
        return false;
    }

    public List<CartaoEmbarque> listarPorPassageiro(int passageiroId) {
        List<CartaoEmbarque> lista = new ArrayList<>();
        String sql = """
            SELECT ce.*, 
                   t.codigo AS ticketCod, 
                   p.nome AS passNome,
                   v.dataVoo, v.id as vooId,
                   origem.sigla AS origemSigla, 
                   destino.sigla AS destinoSigla,
                   va.codigoAssento
            FROM cartao_embarque ce
            JOIN checkin c ON ce.checkInId = c.id
            JOIN ticket t ON c.ticketId = t.id
            JOIN passageiro p ON t.passageiroId = p.id
            JOIN voo v ON t.vooId = v.id
            JOIN aeroporto origem ON v.origemId = origem.id
            JOIN aeroporto destino ON v.destinoId = destino.id
            JOIN vooAssento va ON t.assentoId = va.id
            WHERE p.id = ?
            ORDER BY v.dataVoo DESC
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, passageiroId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CartaoEmbarque ce = new CartaoEmbarque();
                    ce.setId(rs.getInt("id"));
                    ce.setPortao(rs.getString("portao"));
                    ce.setDataEmissao(rs.getTimestamp("dataEmissao").toLocalDateTime());

                    // Montar objeto Ticket aninhado
                    Ticket t = new Ticket();
                    t.setCodigo(rs.getString("ticketCod"));

                    // Passageiro
                    Passageiro p = new Passageiro();
                    p.setNome(rs.getString("passNome"));
                    t.setPassageiro(p);

                    // Voo e Aeroportos
                    Voo v = new Voo();
                    v.setId(rs.getInt("vooId"));
                    v.setDataVoo(rs.getTimestamp("dataVoo").toLocalDateTime());

                    Aeroporto aOrigem = new Aeroporto(); aOrigem.setSigla(rs.getString("origemSigla"));
                    Aeroporto aDestino = new Aeroporto(); aDestino.setSigla(rs.getString("destinoSigla"));
                    v.setOrigem(aOrigem);
                    v.setDestino(aDestino);
                    t.setVoo(v);

                    // Assento
                    VooAssento va = new VooAssento();
                    va.setCodigoAssento(rs.getString("codigoAssento"));
                    t.setAssento(va);

                    // Vincular tudo ao Checkin e ao Cartão
                    CheckIn ci = new CheckIn();
                    ci.setTicket(t);
                    ce.setCheckIn(ci);

                    lista.add(ce);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar cartões de embarque.", e);
        }
        return lista;
    }
}