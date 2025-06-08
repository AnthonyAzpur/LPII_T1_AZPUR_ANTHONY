package com.cibertec.model;

import jakarta.persistence.*;

@Entity
@Table(name = "peliculas")
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pelicula")
    private Long idPelicula;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false, length = 50)
    private String genero;

    @Column(nullable = false)
    private int stock;

    public Pelicula() {}

    public Pelicula(String titulo, String genero, int stock) {
        this.titulo = titulo;
        this.genero = genero;
        this.stock = stock;
    }


    public Long getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Long idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Pelicula{" +
                "idPelicula=" + idPelicula +
                ", titulo='" + titulo + '\'' +
                ", genero='" + genero + '\'' +
                ", stock=" + stock +
                '}';
    }
}
