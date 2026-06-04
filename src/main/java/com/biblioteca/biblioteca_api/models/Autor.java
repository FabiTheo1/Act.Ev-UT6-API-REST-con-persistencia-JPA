package com.biblioteca.biblioteca_api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    // Relación OneToMany: Un autor tiene muchos libros
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    @JsonIgnore // Evita la recursión infinita en JSON
    private List<Libro> libros;

    // Getters, Setters y Constructores

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}