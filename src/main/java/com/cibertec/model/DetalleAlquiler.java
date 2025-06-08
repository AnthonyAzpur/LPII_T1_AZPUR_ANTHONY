package com.cibertec.model;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_alquiler")
public class DetalleAlquiler {

    @EmbeddedId
    private DetalleAlquilerId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idAlquiler")  
    @JoinColumn(name = "alquiler_id")
    private Alquiler alquiler;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPelicula")  
    @JoinColumn(name = "pelicula_id")
    private Pelicula pelicula;

    @Column(nullable = false)
    private int cantidad;

    public DetalleAlquiler() {}

    public DetalleAlquiler(Alquiler alquiler, Pelicula pelicula, int cantidad) {
        this.alquiler = alquiler;
        this.pelicula = pelicula;
        this.cantidad = cantidad;
        this.id = new DetalleAlquilerId(alquiler.getId(), pelicula.getIdPelicula());
    }

    public DetalleAlquilerId getId() {
        return id;
    }

    public void setId(DetalleAlquilerId id) {
        this.id = id;
    }

    public Alquiler getAlquiler() {
        return alquiler;
    }

    public void setAlquiler(Alquiler alquiler) {
        this.alquiler = alquiler;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "DetalleAlquiler{" +
                "id=" + id +
                ", cantidad=" + cantidad +
                '}';
    }
}
