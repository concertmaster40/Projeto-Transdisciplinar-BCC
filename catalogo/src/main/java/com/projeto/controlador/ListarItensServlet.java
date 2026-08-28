package com.projeto.controlador;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.projeto.dao.ItemMidiaDAO;
import com.projeto.modelo.ItemMidia;

@WebServlet("/listarItens")
public class ListarItensServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        try {
            ItemMidiaDAO dao = new ItemMidiaDAO();
            List<ItemMidia> listaItens = dao.readAll(); // Busca todos os registros

            request.setAttribute("itens", listaItens);
            request.getRequestDispatcher("listarItens.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Erro ao buscar a lista de itens: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}