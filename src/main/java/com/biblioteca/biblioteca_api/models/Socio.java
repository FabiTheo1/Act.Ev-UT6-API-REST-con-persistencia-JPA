package com.biblioteca.biblioteca_api.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "socios")
@Getter
@Setter
@NoArgsConstructor //Lombok genere el constructor automaticamente
public class Socio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String numeroSocio;

    @ManyToMany
    @JoinTable(
        name = "prestamos",
        joinColumns = @JoinColumn(name = "socio_id"),
        inverseJoinColumns = @JoinColumn(name = "libro_id")
    )
    private List<Libro> librosPrestados = new ArrayList<>();
}