<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.projeto.modelo.ItemMidia" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Item - Catálogo</title>
</head>
<body>
    <% 
        ItemMidia item = (ItemMidia) request.getAttribute("item");
        if (item == null) {
            response.sendRedirect("listar");
            return;
        }
    %>

    <h2>Editar Item do Catálogo</h2>
    
    <!-- O form envia os dados via POST para o AlterarItemServlet -->
    <form action="alterar" method="POST">
        <!-- Campo Oculto para enviar o ID do item ao Servlet -->
        <input type="hidden" name="id" value="<%= item.getId() %>">

        <label>Título:</label><br>
        <input type="text" name="titulo" value="<%= item.getTitulo() %>" required><br><br>

        <label>Autor / Diretor:</label><br>
        <input type="text" name="autor_diretor" value="<%= item.getAutorDiretor() != null ? item.getAutorDiretor() : "" %>"><br><br>

        <label>Ano de Lançamento:</label><br>
        <input type="number" name="ano_lancamento" value="<%= item.getAnoLancamento() %>" required><br><br>

        <label>Gênero:</label><br>
        <input type="text" name="genero" value="<%= item.getGenero() != null ? item.getGenero() : "" %>"><br><br>

        <label>Tipo de Mídia:</label><br>
        <select name="tipo_midia">
            <option value="Livro" <%= "Livro".equals(item.getTipoMidia()) ? "selected" : "" %>>Livro</option>
            <option value="Filme" <%= "Filme".equals(item.getTipoMidia()) ? "selected" : "" %>>Filme</option>
            <option value="Série" <%= "Série".equals(item.getTipoMidia()) ? "selected" : "" %>>Série</option>
        </select><br><br>

        <label>Sinopse:</label><br>
        <textarea name="sinopse" rows="4" cols="30"><%= item.getSinopse() != null ? item.getSinopse() : "" %></textarea><br><br>

        <button type="submit">Salvar Alterações</button>
        <a href="listar">Cancelar</a>
    </form>
</body>
</html>