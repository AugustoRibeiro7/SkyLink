package com.skyLink.aeroporto.dao.db;

import com.skyLink.aeroporto.dao.CompanhiaAereaDaoInterface;
import com.skyLink.aeroporto.model.CompanhiaAerea;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CompanhiaAereaDaoMysql implements CompanhiaAereaDaoInterface {

    @Override
    public void salvar(CompanhiaAerea companhia) {
        // Se já tem ID → atualiza, senão → insere
        if (companhia.getId() != 0 && buscarPorId(companhia.getId()) != null) {
            atualizar(companhia);
            return;
        }

        String sql = "INSERT INTO companhiaAerea (sigla, nome) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, companhia.getSigla());
            ps.setString(2, companhia.getNome());

            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        companhia.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar companhia aérea", e);
        }
    }

    @Override
    public CompanhiaAerea buscarPorId(int id) {
        String sql = "SELECT * FROM companhiaAerea WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return montarCompanhia(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar companhia por ID", e);
        }
        return null;
    }

    @Override
    public CompanhiaAerea[] listarTodos() {
        String sql = "SELECT * FROM companhiaAerea ORDER BY nome";
        List<CompanhiaAerea> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(montarCompanhia(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar companhias aéreas", e);
        }
        return lista.toArray(new CompanhiaAerea[0]);
    }

    @Override
    public void atualizar(CompanhiaAerea companhia) {
        String sql = "UPDATE companhiaAerea SET sigla = ?, nome = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, companhia.getSigla());
            ps.setString(2, companhia.getNome());
            ps.setInt(3, companhia.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar companhia aérea", e);
        }
    }

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM companhiaAerea WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            // Se tiver voos associados, o MySQL já bloqueia
            throw new RuntimeException("Não foi possível deletar: companhia possui voos cadastrados.", e);
        }
    }

    // Método auxiliar privado
    private CompanhiaAerea montarCompanhia(ResultSet rs) throws SQLException {
        CompanhiaAerea c = new CompanhiaAerea();
        c.setId(rs.getInt("id"));
        c.setSigla(rs.getString("sigla"));
        c.setNome(rs.getString("nome"));

        Timestamp tc = rs.getTimestamp("dataCriacao");
        if (tc != null) c.setDataCriacao(tc.toLocalDateTime());

        Timestamp tm = rs.getTimestamp("dataModificacao");
        if (tm != null) c.setDataAtualizacao(tm.toLocalDateTime());

        return c;
    }
}