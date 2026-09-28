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

/**
 * Servlet responsável por consultar e disponibilizar a listagem completa de itens de mídia.
 * <p>
 * Recupera todos os itens cadastrados através de {@link ItemMidiaDAO#readAll()} e os encaminha
 * como atributo de requisição para a página de listagem ({@code listarItens.jsp}).
 * </p>
 * 
 * @author Nicolas Andreas Jackel
 * @version 1.0
 */
@WebServlet("/listarItens")
public class ListarItensServlet extends HttpServlet {

    /**
     * Processa a requisição GET buscando todos os itens de mídia e despachando para a JSP de listagem.
     * 
     * @param request  objeto {@link HttpServletRequest} onde a lista de itens é armazenada como atributo
     * @param response objeto {@link HttpServletResponse} para despacho da resposta
     * @throws ServletException se ocorrer um erro durante a recuperação dos dados ou despacho
     * @throws IOException      se ocorrer um erro de entrada/saída durante o encaminhamento
     */
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

    /**
     * Processa requisições POST redirecionando o fluxo para o método {@link #doGet(HttpServletRequest, HttpServletResponse)}.
     * 
     * @param request  objeto {@link HttpServletRequest} contendo a requisição do cliente
     * @param response objeto {@link HttpServletResponse} contendo a resposta para o cliente
     * @throws ServletException se ocorrer um erro interno no servlet
     * @throws IOException      se ocorrer um erro de entrada/saída
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}