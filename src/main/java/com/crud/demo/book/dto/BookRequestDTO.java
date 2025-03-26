package com.crud.demo.book.dto;

public class BookRequestDTO {
    private String nome;
    private String categoria;

    public BookRequestDTO() {}

    public BookRequestDTO(String nome, String categoria) {
        this.nome = nome;
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
