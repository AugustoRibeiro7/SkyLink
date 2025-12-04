package com.skyLink.aeroporto.dao.db;

import com.skyLink.aeroporto.dao.PassageiroDaoInterface;
import com.skyLink.aeroporto.model.Passageiro;
import com.skyLink.aeroporto.model.Perfil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PassageiroDaoMysql implements PassageiroDaoInterface {

    @Override
    public void salvar(Passageiro p) {
        if (p.getId() != 0 && buscarPorId(p.getId()) != null) {
            atualizar(p);
            return;
        }
        // Adicionamos a coluna 'perfil' no SQL
        String sql = "INSERT INTO passageiro (nome, dataNascimento, cpf, login, senha, perfil) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getNome());
            ps.setDate(2, Date.valueOf(p.getNascimento()));
            ps.setString(3, p.getDocumento());
            ps.setString(4, p.getLogin());
            ps.setString(5, p.getSenha());

            // Converte o Enum para String para salvar no banco
            String perfilStr = (p.getPerfil() != null) ? p.getPerfil().toString() : "CLIENTE";
            ps.setString(6, perfilStr);

            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) p.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
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
        // Adicionei ", perfil = ?" no SQL
        String sql = "UPDATE passageiro SET nome = ?, dataNascimento = ?, cpf = ?, " +
                "login = ?, senha = ?, perfil = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNome());
            ps.setDate(2, Date.valueOf(p.getNascimento()));
            ps.setString(3, p.getDocumento());
            ps.setString(4, p.getLogin());
            ps.setString(5, p.getSenha());

            // Converte o Enum para String para salvar no banco
            // Se for null, salva como CLIENTE por segurança
            String perfilStr = (p.getPerfil() != null) ? p.getPerfil().toString() : "CLIENTE";
            ps.setString(6, perfilStr);

            ps.setInt(7, p.getId()); // O ID é o último parâmetro (WHERE id = ?)

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
        // 1. Tenta ler o perfil do banco
        String perfilStr = rs.getString("perfil");
        Perfil perfil;
        try {
            // Se o banco devolver null ou texto inválido, assume CLIENTE
            perfil = (perfilStr != null) ? Perfil.valueOf(perfilStr) : Perfil.CLIENTE;
        } catch (IllegalArgumentException e) {
            perfil = Perfil.CLIENTE;
        }

        // 2. Chama o construtor novo com a ordem EXATA dos argumentos
        Passageiro p = new Passageiro(
                rs.getInt("id"),                                // 1. id
                rs.getString("nome"),                           // 2. nome
                rs.getDate("dataNascimento").toLocalDate(),     // 3. nascimento
                rs.getString("cpf"),                            // 4. documento
                rs.getString("login"),                          // 5. login
                rs.getString("senha"),                          // 6. senha
                perfil,                                         // 7. PERFIL (Novo!)
                null,                                           // 8. dataCriacao (null aqui é ok)
                null                                            // 9. dataAtualizacao (null aqui é ok)
        );

        // 3. Preenche as datas se existirem
        Timestamp tc = rs.getTimestamp("dataCriacao");
        if (tc != null) p.setDataCriacao(tc.toLocalDateTime());

        Timestamp tm = rs.getTimestamp("dataModificacao");
        if (tm != null) p.setDataAtualizacao(tm.toLocalDateTime());

        return p;
    }
}