package com.biblioteca.biblioteca_api.repositories;

import com.biblioteca.biblioteca_api.models.Libro;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByAutorId(Long autorId);
}