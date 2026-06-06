package com.biblioteca.biblioteca_api.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "libros")
@Getter
@Setter
@NoArgsConstructor // Lombok genera el constructor vacío obligatorio de JPA
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String isbn;
    private Integer anioPublicacion;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor; // Clase de Dani
}