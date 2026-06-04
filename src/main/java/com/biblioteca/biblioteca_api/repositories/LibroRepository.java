package com.biblioteca.biblioteca_api.repositories;

import com.biblioteca.biblioteca_api.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByTituloContainingIgnoreCase(String titulo);

    List<Libro> findByAutorNombreContainingIgnoreCase(String nombreAutor);
}