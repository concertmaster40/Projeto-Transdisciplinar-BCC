package com.projeto.dao;

import com.projeto.modelo.ItemMidia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ItemMidiaDAO {
    // Metodo CREATE no banco
    public void insert(ItemMidia item){
        // "?" serve como segurança contra sql injection
        String sql = "INSERT INTO item_midia (titulo, autorDiretor, anoLancamento, genero, sinopse, tipoMidia) VALUES (?, ?, ?, ?, ?, ?);"
    }
}
