package com.biblioteca.biblioteca_api.controllers;

import com.biblioteca.biblioteca_api.exceptions.ResourceNotFoundException;
import com.biblioteca.biblioteca_api.models.Socio;
import com.biblioteca.biblioteca_api.services.SocioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/socios")
@RequiredArgsConstructor

public class SocioController {
    private final SocioService socioService;

    @GetMapping
    public ResponseEntity<List<Socio>> listarTodos() {
        return ResponseEntity.ok(socioService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Socio> obtenerPorId(@PathVariable Long id) {
        Socio socio = socioService.obtenerPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró ningún socio con el ID: " + id));

        return ResponseEntity.ok(socio);
    }

    @GetMapping("/{id}/total-libros")
    public ResponseEntity<Long> contarLibrosPrestados(@PathVariable Long id) {
        // Verificamos que el socio existe primero
        socioService.obtenerPorId(id)
            .orElseThrow(() -> new ResourceNotFoundException("Socio no encontrado con ID: " + id));

        return ResponseEntity.ok(socioService.contarLibrosDeSocio(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Socio> actualizar(@PathVariable Long id, @Valid @RequestBody Socio socioActualizado) {
        Socio socioExistente = socioService.obtenerPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Socio no encontrado con ID: " + id));

        socioExistente.setNombre(socioActualizado.getNombre());
        socioExistente.setNumeroSocio(socioActualizado.getNumeroSocio());

        return ResponseEntity.ok(socioService.guardar(socioExistente));
    }

    @PostMapping
    public ResponseEntity<Socio> crear(@Valid @RequestBody Socio socio) {
        Socio nuevoSocio = socioService.guardar(socio);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoSocio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean eliminado = socioService.eliminar(id);
        if (!eliminado) {
            throw new ResourceNotFoundException("Error al eliminar: No se encontró ningún socio con el ID " + id);
        }
        return ResponseEntity.noContent().build();
    }
}
