<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.projeto.modelo.ItemMidia" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Item - Catálogo</title>
    <link rel="stylesheet" type="text/css" href="css/estilo.css">
</head>
<body>

    <% 
        ItemMidia item = (ItemMidia) request.getAttribute("item");
        if (item != null) {
    %>
        <h2>✏️ Editar Item do Catálogo</h2>
        
        <!-- Formulário para envio dos dados atualizados via POST para AlterarItemServlet -->
        <form action="alterar" method="POST">
            <!-- Campo oculto com o ID do registro -->
            <input type="hidden" name="id" value="<%= item.getId() %>">

            <p style="margin-bottom: 15px; color: #64748b;"><strong>ID do Item:</strong> #<%= item.getId() %></p>

            <label for="titulo">Título:</label>
            <input type="text" id="titulo" name="titulo" value="<%= item.getTitulo() %>" required>

            <label for="autor_diretor">Autor / Diretor:</label>
            <input type="text" id="autor_diretor" name="autor_diretor" value="<%= item.getAutorDiretor() != null ? item.getAutorDiretor() : "" %>">

            <label for="ano_lancamento">Ano de Lançamento:</label>
            <input type="number" id="ano_lancamento" name="ano_lancamento" value="<%= item.getAnoLancamento() != null ? item.getAnoLancamento() : "" %>" required>

            <label for="genero">Gênero:</label>
            <input type="text" id="genero" name="genero" value="<%= item.getGenero() != null ? item.getGenero() : "" %>">

            <label for="tipo_midia">Tipo de Mídia:</label>
            <select id="tipo_midia" name="tipo_midia">
                <option value="Livro" <%= "Livro".equals(item.getTipoMidia()) ? "selected" : "" %>>Livro</option>
                <option value="Filme" <%= "Filme".equals(item.getTipoMidia()) ? "selected" : "" %>>Filme</option>
                <option value="Série" <%= "Série".equals(item.getTipoMidia()) ? "selected" : "" %>>Série</option>
            </select>

            <label for="sinopse">Sinopse:</label>
            <textarea id="sinopse" name="sinopse" rows="4" cols="30"><%= item.getSinopse() != null ? item.getSinopse() : "" %></textarea>

            <div style="margin-top: 15px;">
                <button type="submit">Salvar Alterações</button>
                <a href="listarItens" class="btn" style="background-color: #64748b; margin-left: 10px;">Cancelar</a>
                <a href="index.jsp" style="margin-left: 15px;">Página Inicial</a>
            </div>
        </form>

    <% 
        } else { 
    %>
        <!-- Caso o usuário acesse a página diretamente sem passar pela Servlet com um item carregado -->
        <h2>✏️ Editar Item por ID</h2>
        
        <form action="alterar" method="GET">
            <p style="margin-bottom: 15px;">Informe o ID do item que você deseja carregar para edição:</p>
            
            <label for="id">ID do Item:</label>
            <input type="number" id="id" name="id" min="1" required placeholder="Ex: 1">

            <div style="margin-top: 15px;">
                <button type="submit">Carregar Item para Edição</button>
                <a href="listarItens" class="btn" style="background-color: #64748b; margin-left: 10px;">Ver Todos os Itens</a>
                <a href="index.jsp" style="margin-left: 15px;">Página Inicial</a>
            </div>
        </form>
    <% 
        } 
    %>

</body>
</html>