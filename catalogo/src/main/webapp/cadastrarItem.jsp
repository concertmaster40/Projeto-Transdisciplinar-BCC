<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cadastrar Item - Catálogo</title>
</head>
<body>
    <h2>Cadastrar Novo Item no Catálogo</h2>
    
    <!-- O form envia os dados via POST para o nosso Servlet -->
    <form action="cadastrar" method="POST">
        <label>Título:</label><br>
        <input type="text" name="titulo" required><br><br>

        <label>Autor / Diretor:</label><br>
        <input type="text" name="autor_diretor"><br><br>

        <label>Ano de Lançamento:</label><br>
        <input type="number" name="ano_lancamento"><br><br>

        <label>Gênero:</label><br>
        <input type="text" name="genero"><br><br>

        <label>Tipo de Mídia:</label><br>
        <select name="tipo_midia">
            <option value="Livro">Livro</option>
            <option value="Filme">Filme</option>
            <option value="Série">Série</option>
        </select><br><br>

        <label>Sinopse:</label><br>
        <textarea name="sinopse" rows="4" cols="30"></textarea><br><br>

        <button type="submit">Salvar Item</button>
    </form>
</body>
</html>