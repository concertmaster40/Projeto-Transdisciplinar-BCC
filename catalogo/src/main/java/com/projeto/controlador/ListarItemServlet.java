package com.projeto.controlador;

import com.projeto.dao.ItemMidiaDAO;
import com.projeto.modelo.ItemMidia;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/listar")
public class ListarItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        try {
            ItemMidiaDAO dao = new ItemMidiaDAO();
            List<ItemMidia> listaItens = dao.readAll();

            // Atribui a lista de itens à requisição
            request.setAttribute("itens", listaItens);

            // Redireciona a requisição para a página JSP exibir a tabela
            request.getRequestDispatcher("listarItens.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Erro ao buscar a lista de itens: " + e.getMessage());
        }
    }
}