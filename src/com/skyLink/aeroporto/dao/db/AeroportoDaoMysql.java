package com.skyLink.aeroporto.dao.db;

import com.skyLink.aeroporto.dao.AeroportoDaoInterface;
import com.skyLink.aeroporto.model.Aeroporto;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AeroportoDaoMysql implements AeroportoDaoInterface {

    @Override
    public void salvar(Aeroporto a) {
        if (a.getId() != 0 && buscarPorId(a.getId()) != null) {
            atualizar(a);
            return;
        }

        String sql = "INSERT INTO aeroporto (sigla, nome, cidade) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, a.getSigla());
            ps.setString(2, a.getNome());
            ps.setString(3, a.getCidade());

            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        a.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry") || e.getSQLState().equals("23000")) {
                throw new IllegalArgumentException("Sigla já cadastrada.");
            }
            throw new RuntimeException("Erro ao salvar aeroporto: " + e.getMessage(), e);
        }
    }

    @Override
    public Aeroporto buscarPorId(int id) {
        String sql = "SELECT * FROM aeroporto WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return montarAeroporto(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar aeroporto por ID", e);
        }
        return null;
    }

    @Override
    public Aeroporto[] listarTodos() {
        String sql = "SELECT * FROM aeroporto ORDER BY nome";
        List<Aeroporto> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(montarAeroporto(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar aeroportos", e);
        }

        return lista.toArray(new Aeroporto[0]);
    }

    @Override
    public void atualizar(Aeroporto a) {
        String sql = "UPDATE aeroporto SET sigla = ?, nome = ?, cidade = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, a.getSigla());
            ps.setString(2, a.getNome());
            ps.setString(3, a.getCidade());
            ps.setInt(4, a.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar aeroporto", e);
        }
    }

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM aeroporto WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar aeroporto (pode estar sendo usado em voos)", e);
        }
    }

    // MÉTODO AUXILIAR — monta o objeto a partir do ResultSet
    private Aeroporto montarAeroporto(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String sigla = rs.getString("sigla");
        String nome = rs.getString("nome");
        String cidade = rs.getString("cidade");

        // Construtor exige id, nome, cidade, sigla, dataCriacao, dataAtualizacao
        Aeroporto a = new Aeroporto(id, nome, cidade, sigla);

        Timestamp tc = rs.getTimestamp("dataCriacao");
        if (tc != null) a.setDataCriacao(tc.toLocalDateTime());

        Timestamp tm = rs.getTimestamp("dataModificacao");
        if (tm != null) a.setDataAtualizacao(tm.toLocalDateTime());

        return a;
    }
}