package com.phoenixtech.model;

import java.sql.Timestamp;

public class Postagem {
    private int id;
    private Integer autorId;
    private String titulo;
    private String texto;
    private String categoria;
    private Timestamp criadoEm;

    public Postagem() {
    }

    public Postagem(int id, Integer autorId, String titulo, String texto, String categoria, Timestamp criadoEm) {
        this.id = id;
        this.autorId = autorId;
        this.titulo = titulo;
        this.texto = texto;
        this.categoria = categoria;
        this.criadoEm = criadoEm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getAutorId() {
        return autorId;
    }

    public void setAutorId(Integer autorId) {
        this.autorId = autorId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Timestamp getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Timestamp criadoEm) {
        this.criadoEm = criadoEm;
    }

    @Override
    public String toString() {
        return "Postagem{" +
                "id=" + id +
                ", autorId=" + autorId +
                ", titulo='" + titulo + '\'' +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
