package com.projeto.controlador;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.projeto.dao.ItemMidiaDAO;
import com.projeto.modelo.ItemMidia;

/**
 * Servlet responsável por consultar e exibir os detalhes de um item de mídia específico.
 * <p>
 * Busca o item pelo identificador {@code id} via {@link ItemMidiaDAO} e o encaminha para
 * a página de visualização detalhada ({@code listarItem.jsp}).
 * </p>
 * 
 * @author Nicolas Andreas Jackel
 * @version 1.0
 */
@WebServlet("/listarItem")
public class ListarItemServlet extends HttpServlet {

    /**
     * Processa a requisição GET para buscar um item pelo seu ID e encaminhá-lo para a JSP de exibição.
     * Caso o item não seja localizado ou o ID não seja fornecido, redireciona para a listagem geral.
     * 
     * @param request  objeto {@link HttpServletRequest} contendo o parâmetro {@code id} do item
     * @param response objeto {@link HttpServletResponse} para despacho ou redirecionamento
     * @throws ServletException se ocorrer um erro durante o despacho para a JSP
     * @throws IOException      se ocorrer um erro de entrada/saída durante o redirecionamento
     */
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