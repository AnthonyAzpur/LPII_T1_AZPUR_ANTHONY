package com.cibertec.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DetalleAlquilerId implements Serializable {

    @Column(name = "alquiler_id")
    private Long idAlquiler;

    @Column(name = "pelicula_id")
    private Long idPelicula;

    public DetalleAlquilerId() {}

    public DetalleAlquilerId(Long idAlquiler, Long idPelicula) {
        this.idAlquiler = idAlquiler;
        this.idPelicula = idPelicula;
    }

    public Long getIdAlquiler() {
        return idAlquiler;
    }

    public void setIdAlquiler(Long idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

    public Long getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Long idPelicula) {
        this.idPelicula = idPelicula;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetalleAlquilerId)) return false;
        DetalleAlquilerId that = (DetalleAlquilerId) o;
        return Objects.equals(idAlquiler, that.idAlquiler) &&
               Objects.equals(idPelicula, that.idPelicula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAlquiler, idPelicula);
    }
}
