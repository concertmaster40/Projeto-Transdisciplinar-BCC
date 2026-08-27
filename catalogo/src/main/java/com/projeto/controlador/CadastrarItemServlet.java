package com.projeto.controlador;

import com.projeto.dao.ItemMidiaDAO;
import com.projeto.modelo.ItemMidia;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// A anotação abaixo diz qual URL aciona esta classe (a mesma do 'action' no formulário)
@WebServlet("/cadastrar")
public class CadastrarItemServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 1. Receber os dados que vieram do formulário HTML
        String titulo = request.getParameter("titulo");
        String autorDiretor = request.getParameter("autor_diretor");
        String anoString = request.getParameter("ano_lancamento");
        String genero = request.getParameter("genero");
        String tipoMidia = request.getParameter("tipo_midia");
        String sinopse = request.getParameter("sinopse");

        // Convertendo o ano (que vem como texto do HTML) para Integer
        Integer anoLancamento = null;
        if (anoString != null && !anoString.isEmpty()) {
            anoLancamento = Integer.parseInt(anoString);
        }

        // 2. Montar o objeto ItemMidia
        ItemMidia item = new ItemMidia(anoLancamento, autorDiretor, genero, sinopse, tipoMidia, titulo);

        // 3. Chamar o DAO para salvar no banco de dados
        try {
            ItemMidiaDAO dao = new ItemMidiaDAO();
            dao.insert(item);
            
            // Retorno simples para a tela de que deu certo
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<h3>Item '" + titulo + "' cadastrado com sucesso!</h3>");
            out.println("<a href='cadastrarItem.jsp'>Voltar</a>");
            
        } catch (Exception e) {
            throw new ServletException("Erro ao salvar o item: " + e.getMessage());
        }
    }
}