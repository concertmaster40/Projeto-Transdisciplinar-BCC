package com.projeto.controlador;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.projeto.dao.ItemMidiaDAO;
import com.projeto.modelo.ItemMidia;

@WebServlet("/listarItem")
public class ListarItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        String idString = request.getParameter("id");

        if (idString != null && !idString.isEmpty()) {
            Integer id = Integer.parseInt(idString);
            ItemMidiaDAO dao = new ItemMidiaDAO();
            ItemMidia item = dao.read(id); // Busca apenas um registro pelo ID

            if (item != null) {
                request.setAttribute("item", item);
                request.getRequestDispatcher("listarItem.jsp").forward(request, response);
                return;
            }
        }

        // Se não encontrar o ID ou for nulo, volta para a lista geral
        response.sendRedirect("listarItens");
    }
}