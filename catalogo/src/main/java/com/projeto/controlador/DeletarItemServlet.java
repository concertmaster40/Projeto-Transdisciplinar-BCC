package com.projeto.controlador;

import com.projeto.dao.ItemMidiaDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// A anotação abaixo diz qual URL aciona esta classe (ex: action="excluir" ou link/botão)
@WebServlet("/excluir")
public class DeletarItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 1. Receber o ID do item que veio via parâmetro na requisição (ex: /excluir?id=5)
        String idString = request.getParameter("id");

        // Convertendo o ID (que vem como texto do HTML) para Integer/Long
        Integer id = null;
        if (idString != null && !idString.isEmpty()) {
            id = Integer.parseInt(idString);
        }

        // 2. Chamar o DAO para remover do banco de dados
        try {
            if (id != null) {
                ItemMidiaDAO dao = new ItemMidiaDAO();
                dao.delete(id); // Supondo que seu DAO possua o método delete(int/Integer id)
                
                // Retorno simples para a tela de que deu certo
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<h3>Item com ID " + id + " excluído com sucesso!</h3>");
                out.println("<a href='listarItens.jsp'>Voltar para a lista</a>");
            } else {
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<h3>ID inválido ou não informado!</h3>");
                out.println("<a href='listarItens.jsp'>Voltar</a>");
            }
            
        } catch (Exception e) {
            throw new ServletException("Erro ao excluir o item: " + e.getMessage());
        }
    }
}