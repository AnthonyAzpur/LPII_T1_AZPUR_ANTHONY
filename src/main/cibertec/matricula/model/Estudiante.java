package com.cibertec.matricula.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;



@Entity // Marca esta clase como una tabla de base de datos
public class Estudiante {

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL)
    private java.util.List<Matricula> matriculas = new java.util.ArrayList<>();

    @Id // Marca este campo como la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    // Constructores, Getters y Setters (necesarios para que Hibernate funcione)
    public Estudiante() {}

    public Estudiante(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    @Override
    public String toString() {
        return "Estudiante{" + "id=" + id + ", nombre='" + nombre + "', apellido='" + apellido + "'}";
    }
}