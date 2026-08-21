package com.projeto.dao;

import com.projeto.modelo.ItemMidia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ItemMidiaDAO {
    
    // Método CREATE no banco
    public void insert(ItemMidia item) {
        // "?" serve como segurança contra sql injection
        String sql = "INSERT INTO item_midia (titulo, autor_diretor, ano_lancamento, genero, sinopse, tipo_midia) VALUES (?, ?, ?, ?, ?, ?)";
        
        // try-with-resources garante que a conexão e o statement serão fechados automaticamente
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Substituindo as "?" pelos valores do objeto
            stmt.setString(1, item.getTitulo());
            stmt.setString(2, item.getAutor_diretor());
            stmt.setInt(3, item.getAno_lancamento());
            stmt.setString(4, item.getGenero());
            stmt.setString(5, item.getSinopse());
            stmt.setString(6, item.getTipo_midia());
            
            stmt.executeUpdate(); // Executa o comando no banco de dados
            System.out.println("Item inserido com sucesso!");
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir item no banco: " + e.getMessage());
        }
    }
}