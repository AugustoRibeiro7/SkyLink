package com.skyLink.aeroporto.dao.db;

import com.skyLink.aeroporto.dao.PassageiroDaoInterface;
import com.skyLink.aeroporto.model.Passageiro;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PassageiroDaoMysql implements PassageiroDaoInterface {

    @Override
    public void salvar(Passageiro p) {
        // Se já tem ID → atualiza, senão → insere
        if (p.getId() != 0 && buscarPorId(p.getId()) != null) {
            atualizar(p);
            return;
        }

        String sql = "INSERT INTO passageiro (nome, dataNascimento, cpf, login, senha) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getNome());
            ps.setDate(2, Date.valueOf(p.getNascimento()));
            ps.setString(3, p.getDocumento());
            ps.setString(4, p.getLogin());
            ps.setString(5, p.getSenha()); // ← depois vamos hashear

            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        p.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getMessage().contains("Duplicate entry")) {
                throw new IllegalArgumentException("Login ou documento já cadastrado.");
            }
            throw new RuntimeException("Erro ao salvar passageiro", e);
        }
    }

    @Override
    public Passageiro buscarPorId(int id) {
        String sql = "SELECT * FROM passageiro WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return montarPassageiro(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar passageiro por ID", e);
        }
        return null;
    }

    @Override
    public Passageiro[] listarTodos() {
        String sql = "SELECT * FROM passageiro ORDER BY nome";
        List<Passageiro> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(montarPassageiro(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar passageiros", e);
        }
        return lista.toArray(new Passageiro[0]);
    }

    @Override
    public void atualizar(Passageiro p) {
        String sql = "UPDATE passageiro SET nome = ?, dataNascimento = ?, cpf = ?, " +
                "login = ?, senha = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNome());
            ps.setDate(2, Date.valueOf(p.getNascimento()));
            ps.setString(3, p.getDocumento());
            ps.setString(4, p.getLogin());
            ps.setString(5, p.getSenha());
            ps.setInt(6, p.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar passageiro", e);
        }
    }

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM passageiro WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar passageiro (pode ter tickets)", e);
        }
    }

    public Passageiro buscarPorLogin(String login) {
        String sql = "SELECT * FROM passageiro WHERE login = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return montarPassageiro(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar passageiro por login", e);
        }
        return null;
    }

    // MÉTODO AUXILIAR — monta o objeto a partir do ResultSet
    private Passageiro montarPassageiro(ResultSet rs) throws SQLException {
        Passageiro p = new Passageiro(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getDate("dataNascimento").toLocalDate(),
                rs.getString("cpf"),
                rs.getString("login"),
                rs.getString("senha"),
                null, null
        );

        Timestamp tc = rs.getTimestamp("dataCriacao");
        if (tc != null) p.setDataCriacao(tc.toLocalDateTime());

        Timestamp tm = rs.getTimestamp("dataModificacao");
        if (tm != null) p.setDataAtualizacao(tm.toLocalDateTime());

        return p;
    }
}