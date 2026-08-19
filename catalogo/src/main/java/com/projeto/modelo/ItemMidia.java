package com.projeto.modelo;

class ItemMidia{

    private Integer id;
    private String titulo;
    private String autor_diretor;
    private Integer ano_lancamento;
    private String genero;
    private String sinopse;
    private String tipo_middia;

    public ItemMidia(){}

    public ItemMidia(Integer ano_lancamento, String autor_diretor, String genero, String sinopse, String tipo_middia, String titulo) {
        this.ano_lancamento = ano_lancamento;
        this.autor_diretor = autor_diretor;
        this.genero = genero;
        this.sinopse = sinopse;
        this.tipo_middia = tipo_middia;
        this.titulo = titulo;
    }

    public Integer getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor_diretor() {
        return autor_diretor;
    }

    public Integer getAno_lancamento() {
        return ano_lancamento;
    }

    public String getGenero() {
        return genero;
    }

    public String getSinopse() {
        return sinopse;
    }

    public String getTipo_middia() {
        return tipo_middia;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor_diretor(String autor_diretor) {
        this.autor_diretor = autor_diretor;
    }

    public void setAno_lancamento(Integer ano_lancamento) {
        this.ano_lancamento = ano_lancamento;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public void setTipo_middia(String tipo_middia) {
        this.tipo_middia = tipo_middia;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ItemMidia{");
        sb.append("id=").append(id);
        sb.append(", titulo=").append(titulo);
        sb.append(", autor_diretor=").append(autor_diretor);
        sb.append(", ano_lancamento=").append(ano_lancamento);
        sb.append(", genero=").append(genero);
        sb.append(", sinopse=").append(sinopse);
        sb.append(", tipo_middia=").append(tipo_middia);
        sb.append('}');
        return sb.toString();
    }

    
    
}