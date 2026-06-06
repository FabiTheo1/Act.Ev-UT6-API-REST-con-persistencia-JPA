package com.biblioteca.biblioteca_api.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El numero del socio no puede estar en blanco")
    private String numeroSocio;

    @ManyToMany
    @JoinTable(
        name = "prestamos",
        joinColumns = @JoinColumn(name = "socio_id"),
        inverseJoinColumns = @JoinColumn(name = "libro_id")
    )
    private List<Libro> librosPrestados = new ArrayList<>();
}