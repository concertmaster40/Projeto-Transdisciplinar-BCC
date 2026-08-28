<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.projeto.modelo.ItemMidia" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Itens - Catálogo</title>
</head>
<body>
    <h2>Catálogo de Mídias (Todos os Itens)</h2>
    
    <a href="cadastrarItem.jsp"> + Cadastrar Novo Item</a>
    <br><br>

    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
                <th>Título</th>
                <th>Autor / Diretor</th>
                <th>Ano</th>
                <th>Tipo</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<ItemMidia> itens = (List<ItemMidia>) request.getAttribute("itens");
                if (itens != null && !itens.isEmpty()) {
                    for (ItemMidia item : itens) {
            %>
                <tr>
                    <td><%= item.getId() %></td>
                    <td><%= item.getTitulo() %></td>
                    <td><%= item.getAutorDiretor() != null ? item.getAutorDiretor() : "-" %></td>
                    <td><%= item.getAnoLancamento() %></td>
                    <td><%= item.getTipoMidia() %></td>
                    <td>
                        <!-- Detalhar um item especifico -->
                        <a href="listarItem?id=<%= item.getId() %>">Ver Detalhes</a> | 
                        
                        <!-- Editar -->
                        <a href="alterar?id=<%= item.getId() %>">Editar</a> | 
                        
                        <!-- Excluir -->
                        <a href="excluir?id=<%= item.getId() %>" 
                           onclick="return confirm('Tem certeza que deseja excluir este item?');">Excluir</a>
                    </td>
                </tr>
            <% 
                    }
                } else {
            %>
                <tr>
                    <td colspan="6">Nenhum item cadastrado no banco de dados.</td>
                </tr>
            <% 
                } 
            %>
        </tbody>
    </table>
</body>
</html>