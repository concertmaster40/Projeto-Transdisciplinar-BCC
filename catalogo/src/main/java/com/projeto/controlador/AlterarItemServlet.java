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
            try {
                Integer id = Integer.parseInt(idString);
                ItemMidiaDAO dao = new ItemMidiaDAO();
                ItemMidia item = dao.read(id);

                // Se o item existir no banco, envia para a JSP
                if (item != null) {
                    request.setAttribute("item", item);
                    request.getRequestDispatcher("editarItem.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                // ID inválido
            }
        }
        
        // Caso o ID seja inválido ou não encontrado
        response.sendRedirect("listarItens");
    }

    // 2. doPost: Recebe o formulário com os dados alterados e faz o UPDATE
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");

        try {
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

            ItemMidiaDAO dao = new ItemMidiaDAO();
            dao.update(item);

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html><head><meta charset='UTF-8'><title>Sucesso - Edição</title><link rel='stylesheet' type='text/css' href='css/estilo.css'></head><body>");
            out.println("<div class='card' style='text-align:center;'>");
            out.println("<h2>✅ Item '" + titulo + "' atualizado com sucesso!</h2><br>");
            out.println("<a class='btn' href='listarItens'>Voltar para a Lista</a> ");
            out.println("<a class='btn' href='index.jsp' style='background-color: #64748b; margin-left: 10px;'>Página Inicial</a>");
            out.println("</div></body></html>");

        } catch (Exception e) {
            throw new ServletException("Erro ao atualizar o item: " + e.getMessage());
        }
    }
}