package com.cibertec.matricula.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    
    // Teoría: @OneToMany
    // Un Curso tiene muchas Matrículas.
    // mappedBy = "curso": Le dice a JPA "No crees una columna para esta relación en la tabla CURSO.
    // La relación ya está definida por el campo 'curso' en la clase Matricula".
    // cascade = CascadeType.ALL: Si guardo/actualizo/borro un Curso, haz lo mismo con sus matrículas asociadas.
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private List<Matricula> matriculas = new ArrayList<>();

    public Curso() {}

    public Curso(String nombre) {
        this.nombre = nombre;
    }

    // Getters y Setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public List<Matricula> getMatriculas() { return matriculas; }
    public void setMatriculas(List<Matricula> matriculas) { this.matriculas = matriculas; }

    @Override
    public String toString() {
        return "Curso{" + "id=" + id + ", nombre='" + nombre + "'}";
    }
}