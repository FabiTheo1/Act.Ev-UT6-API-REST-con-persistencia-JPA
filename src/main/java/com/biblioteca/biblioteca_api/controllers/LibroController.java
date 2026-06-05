package com.biblioteca.biblioteca_api.controllers;

import com.biblioteca.biblioteca_api.exceptions.ResourceNotFoundException;
import com.biblioteca.biblioteca_api.models.Libro;
import com.biblioteca.biblioteca_api.services.LibroService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/libros")
@RequiredArgsConstructor // Lombok inyecta el servicio automaticamente
public class LibroController {

    private final LibroService libroService;

    @GetMapping
    public ResponseEntity<List<Libro>> listarTodos() {
        return ResponseEntity.ok(libroService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> obtenerPorId(@PathVariable Long id) {
        Libro libro = libroService.obtenerPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró ningún libro con el ID: " + id));
        
        return ResponseEntity.ok(libro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizar(@PathVariable Long id, @Valid @RequestBody Libro libroActualizado) {
        Libro libroExistente = libroService.obtenerPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede actualizar. Libro no encontrado con ID: " + id));

        libroExistente.setTitulo(libroActualizado.getTitulo());
        libroExistente.setIsbn(libroActualizado.getIsbn());
        libroExistente.setAnioPublicacion(libroActualizado.getAnioPublicacion());
        
        Libro libroGuardado = libroService.guardar(libroExistente);
        return ResponseEntity.ok(libroGuardado);
    }

    @PostMapping
    public ResponseEntity<Libro> crear(@RequestBody Libro libro) {
        Libro nuevoLibro = libroService.guardar(libro);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoLibro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean eliminado = libroService.eliminar(id);
        if (!eliminado) {
            throw new ResourceNotFoundException("Error al eliminar: No se encontró ningún libro con el ID " + id);
        }        
        return ResponseEntity.noContent().build();
    }
}