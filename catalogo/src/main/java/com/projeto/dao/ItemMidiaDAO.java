package com.projeto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.projeto.modelo.ItemMidia;

public class ItemMidiaDAO {

    // Método CREATE no banco
    public void insert(ItemMidia item) {
        String sql = "INSERT INTO item_midia (titulo, autor_diretor, ano_lancamento, genero, sinopse, tipo_midia) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getTitulo());
            stmt.setString(2, item.getAutorDiretor());
            
            if (item.getAnoLancamento() != null) {
                stmt.setInt(3, item.getAnoLancamento());
            } else {
                stmt.setNull(3, java.sql.Types.INTEGER);
            }
            
            stmt.setString(4, item.getGenero());
            stmt.setString(5, item.getSinopse());
            stmt.setString(6, item.getTipoMidia());

            stmt.executeUpdate();
            System.out.println("Item inserido com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir item no banco: " + e.getMessage());
        }
    }

    // Método READ no banco (retorna ItemMidia em vez de void)
    public ItemMidia read(Integer id) {
    String sql = "SELECT * FROM item_midia WHERE id = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, id);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return mapearItemMidia(rs); // Limpo e direto
            }
        }

    } catch (SQLException e) {
        throw new RuntimeException("Erro ao buscar item no banco: " + e.getMessage());
    }
    return null;
    }

    // Método UPDATE no banco

    // Método UPDATE no banco
    public void update(ItemMidia item) {
        String sql = "UPDATE item_midia SET titulo = ?, autor_diretor = ?, ano_lancamento = ?, genero = ?, sinopse = ?, tipo_midia = ? WHERE id = ?";
    
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Substituindo as interrogações para a atualização
            stmt.setString(1, item.getTitulo());
            stmt.setString(2, item.getAutorDiretor());
            
            // Tratamento para valor nulo do ano
            if (item.getAnoLancamento() != null) {
                stmt.setInt(3, item.getAnoLancamento());
            } else {
                stmt.setNull(3, java.sql.Types.INTEGER);
            }
        
            stmt.setString(4, item.getGenero());
            stmt.setString(5, item.getSinopse());
            stmt.setString(6, item.getTipoMidia());
        
            // O ID é o último parâmetro (7º '?' da cláusula WHERE)
            stmt.setInt(7, item.getId());
        
            int linhasAfetadas = stmt.executeUpdate();
        
            if (linhasAfetadas > 0) {
                System.out.println("Item atualizado com sucesso!");
            } else {
                System.out.println("Nenhum item encontrado com o ID informado (" + item.getId() + ").");
            }
        
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar item no banco: " + e.getMessage());
        }
    }

    // Método DELETE no banco
    public void delete(Integer id) {
        String sql = "DELETE FROM item_midia WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Item excluído com sucesso");
            } else {
                System.out.println("Nenhum item encontrado com o ID fornecido");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar item do banco: " + e.getMessage());
        }
    }

    // Método utilitário para mapear mídias
    private ItemMidia mapearItemMidia(ResultSet rs) throws SQLException {
    ItemMidia item = new ItemMidia();
    item.setId(rs.getInt("id"));
    item.setTitulo(rs.getString("titulo"));
    item.setAutorDiretor(rs.getString("autor_diretor"));
    item.setAnoLancamento(rs.getInt("ano_lancamento"));
    item.setGenero(rs.getString("genero"));
    item.setSinopse(rs.getString("sinopse"));
    item.setTipoMidia(rs.getString("tipo_midia"));
    return item;
    }
}