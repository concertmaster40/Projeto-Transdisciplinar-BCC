<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.projeto.modelo.ItemMidia" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Excluir Item - Catálogo</title>
    <link rel="stylesheet" type="text/css" href="css/estilo.css">
</head>
<body>

    <h2>🗑️ Excluir Item do Catálogo</h2>

    <% 
        // Se a Servlet tiver passado um item específico para confirmar exclusão
        ItemMidia item = (ItemMidia) request.getAttribute("item");
        if (item != null) {
    %>
        <p>Você tem certeza que deseja excluir o seguinte item?</p>
        <ul>
            <li><strong>ID:</strong> <%= item.getId() %></li>
            <li><strong>Título:</strong> <%= item.getTitulo() %></li>
            <li><strong>Autor/Diretor:</strong> <%= item.getAutorDiretor() != null ? item.getAutorDiretor() : "-" %></li>
            <li><strong>Tipo:</strong> <%= item.getTipoMidia() %></li>
        </ul>

        <form action="excluir" method="POST">
            <input type="hidden" name="id" value="<%= item.getId() %>">
            <button type="submit">Confirmar Exclusão</button>
            <a href="listarItens">Cancelar</a>
        </form>

    <% 
        } else { 
    %>
        <!-- Caso o usuário acesse a página diretamente para informar o ID manualmente -->
        <p>Informe o ID do item que deseja remover do banco de dados:</p>

        <form action="excluir" method="GET" onsubmit="return confirm('Tem certeza que deseja excluir este item?');">
            <label for="id">ID do Item:</label><br>
            <input type="number" id="id" name="id" min="1" required placeholder="Ex: 1"><br><br>

            <button type="submit">Excluir Item</button>
            <a href="listarItens">Cancelar</a>
        </form>
    <% 
        } 
    %>

</body>
</html>