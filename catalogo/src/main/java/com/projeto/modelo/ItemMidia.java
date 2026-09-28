package com.projeto.modelo;

/**
 * Classe base do Item de Mídia, nela se~rao declaradas todas as variáveis existentes e 
 * os métodos getters e setters, seguindo o padrão de POO
 * 
 * @author Nicolas Andreas Jackel
 * @version 1.0
 */
public class ItemMidia{

    private Integer id;
    private String titulo;
    private String autorDiretor;
    private Integer anoLancamento;
    private String genero;
    private String sinopse;
    private String tipoMidia;

    /**
     * Construtor do objeto ItemMidia vazio, necessário para inicialização e conformidade com o padrão JavaBeans.
     */
    public ItemMidia(){}

    /**
     * Construtor do objeto ItemMidia cheio, responsável por inicializar todas as variáveis com os valores passados por parâmetro
     */
    public ItemMidia(Integer anoLancamento, String autorDiretor, String genero, String sinopse, String tipoMidia, String titulo) {
        this.anoLancamento = anoLancamento;
        this.autorDiretor = autorDiretor;
        this.genero = genero;
        this.sinopse = sinopse;
        this.tipoMidia = tipoMidia;
        this.titulo = titulo;
    }

    public Integer getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutorDiretor() {
        return autorDiretor;
    }

    public Integer getAnoLancamento() {
        return anoLancamento;
    }

    public String getGenero() {
        return genero;
    }

    public String getSinopse() {
        return sinopse;
    }

    public String getTipoMidia() {
        return tipoMidia;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutorDiretor(String autorDiretor) {
        this.autorDiretor = autorDiretor;
    }

    public void setAnoLancamento(Integer anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public void setTipoMidia(String tipoMidia) {
        this.tipoMidia = tipoMidia;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ItemMidia{");
        sb.append("id=").append(id);
        sb.append(", titulo=").append(titulo);
        sb.append(", autorDiretor=").append(autorDiretor);
        sb.append(", anoLancamento=").append(anoLancamento);
        sb.append(", genero=").append(genero);
        sb.append(", sinopse=").append(sinopse);
        sb.append(", tipoMidia=").append(tipoMidia);
        sb.append('}');
        return sb.toString();
    }
}