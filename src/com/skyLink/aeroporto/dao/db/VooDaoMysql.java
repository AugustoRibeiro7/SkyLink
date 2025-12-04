package com.skyLink.aeroporto.dao.db;

import com.skyLink.aeroporto.dao.VooDaoInterface;
import com.skyLink.aeroporto.model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VooDaoMysql implements VooDaoInterface {

    private final AeroportoDaoMysql aeroportoDao = new AeroportoDaoMysql();
    private final CompanhiaAereaDaoMysql companhiaDao = new CompanhiaAereaDaoMysql();

    @Override
    public boolean inserir(Voo voo) {
        String sql = """
            INSERT INTO voo 
            (origemId, destinoId, dataVoo, duracao, companhiaAereaId, capacidade, estado) 
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, voo.getOrigem().getId());
            ps.setInt(2, voo.getDestino().getId());
            ps.setTimestamp(3, Timestamp.valueOf(voo.getDataVoo()));
            ps.setInt(4, voo.getDuracaoVoo());
            ps.setInt(5, voo.getCompanhiaAerea().getId()); // ← companhiaAereaId
            ps.setInt(6, voo.getCapacidadeVoo());
            ps.setString(7, voo.getEstado().name());

            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        voo.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir voo: " + e.getMessage(), e);
        }
        return false;
    }

    @Override
    public Voo[] buscar(String origemSigla, String destinoSigla) {
        String sql = """
            SELECT v.*, 
                   a1.sigla AS origem_sigla, a1.nome AS origem_nome, a1.cidade AS origem_cidade,
                   a2.sigla AS destino_sigla, a2.nome AS destino_nome, a2.cidade AS destino_cidade,
                   c.id AS cid, c.sigla AS c_sigla, c.nome AS c_nome
            FROM voo v
            JOIN aeroporto a1 ON v.origemId = a1.id
            JOIN aeroporto a2 ON v.destinoId = a2.id
            JOIN companhiaAerea c ON v.companhiaAereaId = c.id
            WHERE a1.sigla = ? AND a2.sigla = ?
            ORDER BY v.dataVoo
            """;

        List<Voo> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, origemSigla.toUpperCase());
            ps.setString(2, destinoSigla.toUpperCase());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(montarVoo(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar voos", e);
        }
        return lista.toArray(new Voo[0]);
    }

    @Override
    public Voo[] listar() {
        String sql = """
            SELECT v.*, 
                   a1.sigla AS origem_sigla, a1.nome AS origem_nome, a1.cidade AS origem_cidade,
                   a2.sigla AS destino_sigla, a2.nome AS destino_nome, a2.cidade AS destino_cidade,
                   c.id AS cid, c.sigla AS c_sigla, c.nome AS c_nome
            FROM voo v
            JOIN aeroporto a1 ON v.origemId = a1.id
            JOIN aeroporto a2 ON v.destinoId = a2.id
            JOIN companhiaAerea c ON v.companhiaAereaId = c.id
            ORDER BY v.dataVoo
            """;

        List<Voo> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(montarVoo(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar voos", e);
        }
        return lista.toArray(new Voo[0]);
    }

    public boolean atualizarEstado(int vooId, EstadoVooEnum novoEstado) {
        String sql = "UPDATE voo SET estado = ?, dataModificacao = NOW() WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, novoEstado.name());
            ps.setInt(2, vooId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar estado do voo " + vooId, e);
        }
    }

    @Override
    public boolean atualizar(Voo voo, int identificador) {
        String sql = """
            UPDATE voo SET 
            origemId = ?, destinoId = ?, dataVoo = ?, duracao = ?,
            companhiaAereaId = ?, capacidade = ?, estado = ?
            WHERE id = ?
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, voo.getOrigem().getId());
            ps.setInt(2, voo.getDestino().getId());
            ps.setTimestamp(3, Timestamp.valueOf(voo.getDataVoo()));
            ps.setInt(4, voo.getDuracaoVoo());
            ps.setInt(5, voo.getCompanhiaAerea().getId()); // ← companhiaAereaId
            ps.setInt(6, voo.getCapacidadeVoo());
            ps.setString(7, voo.getEstado().name());
            ps.setInt(8, identificador);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar voo ID " + identificador, e);
        }
    }

    @Override
    public boolean deletar(int id) {
        String sql = "DELETE FROM voo WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new IllegalArgumentException("Não é possível excluir o voo porque já existem passagens vendidas ou assentos reservados.");
        }
    }

    public boolean cancelarVoo(int vooId) {
        return atualizarEstado(vooId, EstadoVooEnum.CANCELADO);
    }

    private Voo montarVoo(ResultSet rs) throws SQLException {
        Voo voo = new Voo();

        voo.setId(rs.getInt("id"));

        // Origem
        Aeroporto origem = new Aeroporto();
        origem.setId(rs.getInt("origemId"));
        origem.setSigla(rs.getString("origem_sigla"));
        origem.setNome(rs.getString("origem_nome"));
        origem.setCidade(rs.getString("origem_cidade"));
        voo.setOrigem(origem);

        // Destino
        Aeroporto destino = new Aeroporto();
        destino.setId(rs.getInt("destinoId"));
        destino.setSigla(rs.getString("destino_sigla"));
        destino.setNome(rs.getString("destino_nome"));
        destino.setCidade(rs.getString("destino_cidade"));
        voo.setDestino(destino);

        // Data e duração
        Timestamp ts = rs.getTimestamp("dataVoo");
        voo.setDataVoo(ts != null ? ts.toLocalDateTime() : null);
        voo.setDuracaoVoo(rs.getInt("duracao"));
        voo.setCapacidadeVoo(rs.getInt("capacidade"));

        // Estado
        String estadoStr = rs.getString("estado");
        voo.setEstado(estadoStr != null ? EstadoVooEnum.valueOf(estadoStr) : EstadoVooEnum.PROGRAMADO);

        // Companhia Aérea
        CompanhiaAerea companhia = new CompanhiaAerea();
        companhia.setId(rs.getInt("cid"));
        companhia.setSigla(rs.getString("c_sigla"));
        companhia.setNome(rs.getString("c_nome"));
        voo.setCompanhiaAerea(companhia);

        return voo;
    }
}