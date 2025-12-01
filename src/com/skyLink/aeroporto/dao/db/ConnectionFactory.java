package com.skyLink.aeroporto.dao.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static final Properties config = new Properties();

    static {
        // Busca o arquivo na raiz do src
        try (InputStream is = ConnectionFactory.class
                .getResourceAsStream("/database.properties")) {

            if (is == null) {
                throw new RuntimeException(
                        "Arquivo 'database.properties' não encontrado na raiz do src!\n" +
                                "Crie o arquivo src/database.properties com as chaves db.url, db.user e db.password");
            }
            config.load(is);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar configurações do banco de dados", e);
        }
    }

    public static Connection getConnection() {
        try {
            String url = config.getProperty("db.url");
            String user = config.getProperty("db.user");
            String password = config.getProperty("db.password");

            if (url == null || user == null || password == null) {
                throw new RuntimeException("Configurações do banco incompletas no database.properties");
            }

            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar com o banco de dados: " + e.getMessage(), e);
        }
    }

    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
