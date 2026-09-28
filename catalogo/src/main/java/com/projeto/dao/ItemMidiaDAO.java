package com.projeto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.projeto.modelo.ItemMidia;

/**
 * Data Access Object (DAO) responsável por gerenciar as operações de persistência
 * e manipulação de dados da entidade {@link ItemMidia} na tabela {@code item_midia}.
 * 
 * @author Nicolas Andreas Jackel
 * @version 1.0
 */
public class ItemMidiaDAO {

    /**
     * Insere um novo registro de item de mídia no banco de dados.
     * 
     * @param item objeto {@link ItemMidia} contendo as informações a serem persistidas
     * @throws RuntimeException caso ocorra um erro na execução do comando SQL
     */
    public void insert(ItemMidia item) {
        String sql = "INSERT INTO item_midia (titulo, autor_diretor, ano_lancamento, genero, sinopse, tipo_midia) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getTitulo());
            stmt.setString(2, item.getAutorDiretor());
            stmt.setInt(3, item.getAnoLancamento());
            stmt.setString(4, item.getGenero());
            stmt.setString(5, item.getSinopse());
            stmt.setString(6, item.getTipoMidia());

            stmt.executeUpdate();
            System.out.println("Item inserido com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir item no banco: " + e.getMessage());
        }
    }

    /**
     * Consulta um item de mídia específico no banco de dados com base em seu identificador.
     * 
     * @param id identificador único do item de mídia a ser consultado
     * @return o objeto {@link ItemMidia} correspondente se encontrado, ou {@code null} caso contrário
     * @throws RuntimeException caso ocorra um erro na execução da consulta SQL
     */
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

    /**
     * Atualiza os dados de um registro de item de mídia existente no banco de dados.
     * 
     * @param item objeto {@link ItemMidia} com os dados atualizados e o ID preenchido
     * @throws RuntimeException caso ocorra um erro durante a atualização no banco de dados
     */
    public void update(ItemMidia item) {
        String sql = "UPDATE item_midia SET titulo = ?, autor_diretor = ?, ano_lancamento = ?, genero = ?, sinopse = ?, tipo_midia = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Substituindo as interrogações para a atualização
            stmt.setString(1, item.getTitulo());
            stmt.setString(2, item.getAutorDiretor());
            stmt.setInt(3, item.getAnoLancamento());
            stmt.setString(4, item.getGenero());
            stmt.setString(5, item.getSinopse());
            stmt.setString(6, item.getTipoMidia());
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

    /**
     * Remove um item de mídia do banco de dados pelo seu identificador.
     * 
     * @param id identificador único do item a ser excluído
     * @throws RuntimeException caso ocorra uma falha na exclusão SQL
     */
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

    /**
     * Recupera todos os itens de mídia cadastrados no banco de dados.
     * 
     * @return lista contendo os objetos {@link ItemMidia} encontrados, ou uma lista vazia caso não haja registros
     * @throws RuntimeException caso ocorra um erro durante a consulta SQL
     */
    public java.util.List<ItemMidia> readAll() {
        String sql = "SELECT * FROM item_midia";
        java.util.List<ItemMidia> lista = new java.util.ArrayList<>();
    
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                lista.add(mapearItemMidia(rs)); // Reutiliza seu método de mapeamento!
            }
        
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar itens do banco: " + e.getMessage());
        }
    
        return lista;
    }

    /**
     * Mapeia os dados da linha atual de um {@link ResultSet} para uma nova instância de {@link ItemMidia}.
     * 
     * @param rs objeto {@link ResultSet} posicionado no registro atual
     * @return nova instância preenchida de {@link ItemMidia}
     * @throws SQLException se houver erro ao acessar as colunas do {@link ResultSet}
     */
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