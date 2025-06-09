package com.cibertec.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity // Marca esta clase como una entidad JPA (tabla en la base de datos)
@Table(name = "alquileres") // Nombre de la tabla en la base de datos
public class Alquiler {

    @Id // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID (auto-incremental)
    private Long id;

    @ManyToOne(optional = false) // Muchos alquileres pueden estar asociados a un solo cliente (relación N:1)
    @JoinColumn(name = "cliente_id") // Nombre de la columna FK en la tabla "alquileres"
    private Cliente cliente;

    @Column(nullable = false) // No se permite nulo para la fecha
    private LocalDate fecha;

    @Enumerated(EnumType.STRING) // Guarda el enum como texto en la base de datos
    @Column(nullable = false)
    private EstadoAlquiler estado;

    @Column(nullable = false) // Monto total del alquiler
    private double total;

    @OneToMany(mappedBy = "alquiler", cascade = CascadeType.ALL, orphanRemoval = true)// Relación 1:N con DetalleAlquiler
 
    private List<DetalleAlquiler> detalles = new ArrayList<>();

    public Alquiler() {} 

    // Constructor
    public Alquiler(Cliente cliente, LocalDate fecha, EstadoAlquiler estado, double total) {
        this.cliente = cliente;
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
    }

    // Getters y setters
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

    // Método  para añadir un detalle al alquiler
    public void addDetalle(DetalleAlquiler detalle) {
        detalles.add(detalle);
        detalle.setAlquiler(this); 
    }

    // Método para eliminar un detalle del alquiler
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
