package com.projeto.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe utilitária responsável pelo gerenciamento e fornecimento de conexões com o banco de dados MySQL.
 * 
 * @author Nicolas Andreas Jackel
 * @version 1.0
 */
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/catalogo_db?useTimezone=true&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String SENHA = ""; // Colocar senha MySQL aqui, está vazia por motivos de segurança

    /**
     * Estabelece e retorna uma nova conexão ativa com a base de dados {@code catalogo_db}.
     * 
     * @return objeto {@link Connection} conectado ao banco de dados MySQL
     * @throws RuntimeException caso o driver JDBC não seja encontrado ou ocorra erro de conexão SQL
     */
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Erro ao conectar com o banco de dados: " + e.getMessage());
        }    
    }   
}
