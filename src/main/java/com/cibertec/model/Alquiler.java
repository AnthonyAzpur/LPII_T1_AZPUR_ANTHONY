package com.cibertec.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "alquileres")
public class Alquiler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(nullable = false)
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoAlquiler estado;

    @Column(nullable = false)
    private double total;

    @OneToMany(mappedBy = "alquiler", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleAlquiler> detalles = new ArrayList<>();

    public Alquiler() {}

    public Alquiler(Cliente cliente, LocalDate fecha, EstadoAlquiler estado, double total) {
        this.cliente = cliente;
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public EstadoAlquiler getEstado() {
        return estado;
    }

    public void setEstado(EstadoAlquiler estado) {
        this.estado = estado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<DetalleAlquiler> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleAlquiler> detalles) {
        this.detalles = detalles;
    }

    public void addDetalle(DetalleAlquiler detalle) {
        detalles.add(detalle);
        detalle.setAlquiler(this);
    }

    public void removeDetalle(DetalleAlquiler detalle) {
        detalles.remove(detalle);
        detalle.setAlquiler(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Alquiler)) return false;
        Alquiler alquiler = (Alquiler) o;
        return Objects.equals(id, alquiler.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Alquiler{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", fecha=" + fecha +
                ", estado=" + estado +
                ", total=" + total +
                '}';
    }
}
