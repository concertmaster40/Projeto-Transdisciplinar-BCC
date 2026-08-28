<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home - Catálogo de Mídias</title>
</head>
<body>

    <h1>🎬 Catálogo de Mídias</h1>
    <p>Bem-vindo ao sistema! Escolha uma opção abaixo para navegar:</p>

    <hr>

    <main>
        <!-- 1. Listar todos os itens -->
        <div>
            <h2>
                <a href="listarItens">📋 Ver Listagem Completa</a>
            </h2>
            <p>Visualizar a tabela com todas as mídias cadastradas, editar ou excluir registros.</p>
        </div>

        <br>

        <!-- 2. Cadastrar novo item -->
        <div>
            <h2>
                <a href="cadastrarItem.jsp">➕ Cadastrar Novo Item</a>
            </h2>
            <p>Adicionar um novo livro, filme ou série ao banco de dados.</p>
        </div>

        <br>

        <!-- 3. Buscar/Consultar um item específico por ID -->
        <div>
            <h2>🔍 Consultar Item por ID</h2>
            <form action="listarItem" method="GET">
                <label for="inputIdConsulta">Digite o ID do item:</label>
                <input type="number" id="inputIdConsulta" name="id" min="1" required placeholder="Ex: 1">
                <button type="submit">Buscar Detalhes</button>
            </form>
        </div>

        <br>

        <!-- 4. Deletar um item diretamente por ID -->
        <div>
            <h2>🗑️ Excluir Item por ID</h2>
            <form action="excluir" method="GET" onsubmit="return confirm('Tem certeza que deseja excluir este item?');">
                <label for="inputIdExcluir">Digite o ID a ser excluído:</label>
                <input type="number" id="inputIdExcluir" name="id" min="1" required placeholder="Ex: 1">
                <button type="submit">Excluir Registro</button>
            </form>
        </div>
    </main>

    <hr>

    <footer>
        <p><small>Sistema desenvolvido em Java Servlet + JSP + JDBC</small></p>
    </footer>

</body>
</html>