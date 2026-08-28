package com.projeto.controlador;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.projeto.dao.ItemMidiaDAO;
import com.projeto.modelo.ItemMidia;

@WebServlet("/alterar")
public class AlterarItemServlet extends HttpServlet {

    // 1. doGet: Carrega os dados do item e redireciona para a tela de edição
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String idString = request.getParameter("id");

        if (idString != null && !idString.isEmpty()) {
            Integer id = Integer.parseInt(idString);
            ItemMidiaDAO dao = new ItemMidiaDAO();
            ItemMidia item = dao.read(id);

            // Se o item existir no banco, envia para a JSP
            if (item != null) {
                request.setAttribute("item", item);
                request.getRequestDispatcher("editarItem.jsp").forward(request, response);
                return;
            }
        }
        
        // Caso o ID seja inválido ou não encontrado
        response.sendRedirect("listar");
    }

    // 2. doPost: Recebe o formulário com os dados alterados e faz o UPDATE
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");

        // Capturando o id e os campos do formulário
        Integer id = Integer.parseInt(request.getParameter("id"));
        String titulo = request.getParameter("titulo");
        String autorDiretor = request.getParameter("autor_diretor");
        String anoString = request.getParameter("ano_lancamento");
        String genero = request.getParameter("genero");
        String tipoMidia = request.getParameter("tipo_midia");
        String sinopse = request.getParameter("sinopse");

        Integer anoLancamento = null;
        if (anoString != null && !anoString.isEmpty()) {
            anoLancamento = Integer.parseInt(anoString);
        }

        // Instancia o objeto já com o ID
        ItemMidia item = new ItemMidia(anoLancamento, autorDiretor, genero, sinopse, tipoMidia, titulo);
        item.setId(id);

        try {
            ItemMidiaDAO dao = new ItemMidiaDAO();
            dao.update(item);

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<h3>Item '" + titulo + "' atualizado com sucesso!</h3>");
            out.println("<a href='listar'>Voltar para a lista</a>");

        } catch (Exception e) {
            throw new ServletException("Erro ao atualizar o item: " + e.getMessage());
        }
    }
}