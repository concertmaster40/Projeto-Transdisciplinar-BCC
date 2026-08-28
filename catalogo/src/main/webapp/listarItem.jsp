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

    <h2>Detalhes da Mídia: <%= item.getTitulo() %></h2>

    <p><strong>ID:</strong> <%= item.getId() %></p>
    <p><strong>Título:</strong> <%= item.getTitulo() %></p>
    <p><strong>Autor / Diretor:</strong> <%= item.getAutorDiretor() != null ? item.getAutorDiretor() : "-" %></p>
    <p><strong>Ano de Lançamento:</strong> <%= item.getAnoLancamento() %></p>
    <p><strong>Gênero:</strong> <%= item.getGenero() != null ? item.getGenero() : "-" %></p>
    <p><strong>Tipo de Mídia:</strong> <%= item.getTipoMidia() %></p>
    <p><strong>Sinopse:</strong> <%= item.getSinopse() != null ? item.getSinopse() : "-" %></p>

    <br>
    <a href="alterar?id=<%= item.getId() %>">Editar este item</a> | 
    <a href="listarItens">Voltar para a lista completa</a>
</body>
</html>