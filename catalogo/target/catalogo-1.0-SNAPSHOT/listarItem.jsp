<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.projeto.modelo.ItemMidia" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Detalhes do Item - Catálogo</title>
    <link rel="stylesheet" type="text/css" href="css/estilo.css">
</head>
<body>
    <% 
        ItemMidia item = (ItemMidia) request.getAttribute("item");
        if (item == null) {
            response.sendRedirect("listarItens");
            return;
        }
    %>

    <h2>🔍 Detalhes da Mídia: <%= item.getTitulo() %></h2>

    <div class="card">
        <p><strong>ID:</strong> #<%= item.getId() %></p>
        <p><strong>Título:</strong> <%= item.getTitulo() %></p>
        <p><strong>Autor / Diretor:</strong> <%= item.getAutorDiretor() != null ? item.getAutorDiretor() : "-" %></p>
        <p><strong>Ano de Lançamento:</strong> <%= item.getAnoLancamento() %></p>
        <p><strong>Gênero:</strong> <%= item.getGenero() != null ? item.getGenero() : "-" %></p>
        <p><strong>Tipo de Mídia:</strong> <%= item.getTipoMidia() %></p>
        <p><strong>Sinopse:</strong> <%= item.getSinopse() != null ? item.getSinopse() : "-" %></p>
    </div>

    <div>
        <a href="alterar?id=<%= item.getId() %>" class="btn">✏️ Editar este item</a>
        <a href="listarItens" class="btn" style="background-color: #64748b; margin-left: 10px;">📋 Lista Completa</a>
        <a href="index.jsp" style="margin-left: 15px;">🏠 Página Inicial</a>
    </div>
</body>
</html>