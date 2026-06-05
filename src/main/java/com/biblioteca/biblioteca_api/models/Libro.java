package com.biblioteca.biblioteca_api.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotBlank(message = "El ISBN no puede estar vacío")
    private String isbn;

    @NotNull(message = "El año de publicación es obligatorio")
    private Integer anioPublicacion;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor; // Clase de Dani
}