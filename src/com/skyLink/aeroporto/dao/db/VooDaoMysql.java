package com.skyLink.aeroporto.dao.db;

import com.skyLink.aeroporto.dao.VooDaoInterface;
import com.skyLink.aeroporto.model.Voo;
import com.skyLink.aeroporto.model.CompanhiaAerea;
import com.skyLink.aeroporto.model.EstadoVooEnum;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VooDaoMysql implements VooDaoInterface {

    @Override
    public boolean inserir(Voo voo) {
        String sql = """
            INSERT INTO voo 
            (companhiaAereaId, origem, destino, dataVoo, capacidade, duracao, estado, dataCriacao, dataModificacao) 
            VALUES (?, ?, ?, ?, ?, ?, ?, NOW(), NOW())
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, voo.getCompanhiaAerea().getId());
            ps.setString(2, voo.getOrigem());
            ps.setString(3, voo.getDestino());
            ps.setTimestamp(4, Timestamp.valueOf(voo.getDataVoo()));
            ps.setInt(5, voo.getCapacidadeVoo());
            ps.setInt(6, voo.getDuracaoVoo());
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
    public Voo[] buscar(String origem, String destino) {
        String sql = "SELECT v.*, c.id AS cid, c.sigla, c.nome " +
                "FROM voo v " +
                "JOIN companhiaAerea c ON v.companhiaAereaId = c.id " +
                "WHERE v.origem = ? AND v.destino = ? " +
                "ORDER BY v.dataVoo";

        List<Voo> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, origem);
            ps.setString(2, destino);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(montarVoo(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar voos de " + origem + " para " + destino, e);
        }
        return lista.toArray(new Voo[0]);
    }

    @Override
    public Voo[] listar() {
        String sql = "SELECT v.*, c.id AS cid, c.sigla, c.nome " +
                "FROM voo v " +
                "JOIN companhiaAerea c ON v.companhiaAereaId = c.id " +
                "ORDER BY v.dataVoo";

        List<Voo> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(montarVoo(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos os voos", e);
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
            companhiaAereaId = ?, origem = ?, destino = ?, dataVoo = ?, 
            capacidade = ?, duracao = ?, estado = ?, dataModificacao = NOW() 
            WHERE id = ?
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, voo.getCompanhiaAerea().getId());
            ps.setString(2, voo.getOrigem());
            ps.setString(3, voo.getDestino());
            ps.setTimestamp(4, Timestamp.valueOf(voo.getDataVoo()));
            ps.setInt(5, voo.getCapacidadeVoo());
            ps.setInt(6, voo.getDuracaoVoo());
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
            throw new IllegalArgumentException("Não é possível excluir o voo porque já existem passagens vendidas.");
        }
    }

    public boolean cancelarVoo(int vooId) {
        return atualizarEstado(vooId, EstadoVooEnum.CANCELADO);
    }

    // Método auxiliar para montar o objeto Voo com CompanhiaAerea completa
    private Voo montarVoo(ResultSet rs) throws SQLException {
        Voo voo = new Voo();

        // Dados do voo
        voo.setId(rs.getInt("id"));
        voo.setOrigem(rs.getString("origem"));
        voo.setDestino(rs.getString("destino"));
        Timestamp ts = rs.getTimestamp("dataVoo");
        voo.setDataVoo(ts != null ? ts.toLocalDateTime() : null);
        voo.setCapacidadeVoo(rs.getInt("capacidade"));
        voo.setDuracaoVoo(rs.getInt("duracao"));

        String estadoStr = rs.getString("estado");
        voo.setEstado(estadoStr != null ? EstadoVooEnum.valueOf(estadoStr) : EstadoVooEnum.PROGRAMADO);

        Timestamp tc = rs.getTimestamp("dataCriacao");
        voo.setDataCriacao(tc != null ? tc.toLocalDateTime() : LocalDateTime.now());
        Timestamp tm = rs.getTimestamp("dataModificacao");
        voo.setDataModificacao(tm != null ? tm.toLocalDateTime() : LocalDateTime.now());

        // Montando a CompanhiaAerea
        CompanhiaAerea companhia = new CompanhiaAerea();
        companhia.setId(rs.getInt("cid"));
        companhia.setSigla(rs.getString("sigla"));
        companhia.setNome(rs.getString("nome"));
        voo.setCompanhiaAerea(companhia);

        return voo;
    }
}