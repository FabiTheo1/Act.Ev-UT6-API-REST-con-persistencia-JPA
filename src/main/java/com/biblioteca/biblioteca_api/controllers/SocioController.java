package com.biblioteca.biblioteca_api.controllers;

import com.biblioteca.biblioteca_api.exceptions.ResourceNotFoundException;
import com.biblioteca.biblioteca_api.models.Socio;
import com.biblioteca.biblioteca_api.services.SocioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/autores")
@RequiredArgsConstructor // Lombok inyecta el servicio automaticamente

public class SocioController {
    private final SocioService socioService;

    @GetMapping
    public ResponseEntity<List<Socio>> listarTodos() {
        return ResponseEntity.ok(socioService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Socio> obtenerPorId(@PathVariable Long id) {
        Socio socio = socioService.obtenerPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró ningún libro con el ID: " + id));

        return ResponseEntity.ok(socio);
    }

    @PostMapping
    public ResponseEntity<Socio> crear(@RequestBody Socio socio) {
        Socio nuevoAutor = socioService.guardar(socio);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAutor);
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
