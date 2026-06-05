package com.biblioteca.biblioteca_api.controllers;

import com.biblioteca.biblioteca_api.exceptions.ResourceNotFoundException;
import com.biblioteca.biblioteca_api.models.Autor;
import com.biblioteca.biblioteca_api.services.AutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/autores")
@RequiredArgsConstructor // Lombok inyecta el servicio automaticamente

public class AutorController {
    private final AutorService autorService;

    @GetMapping
    public ResponseEntity<List<Autor>> listarTodos() {
        return ResponseEntity.ok(autorService.obtenerTodos());
    }

    @GetMapping("/buscar")
    public ResponseEntity<Autor> obtenerPorNombre(@RequestParam(required = false) String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Autor autor = autorService.obtenerPorNombre(nombre) // Debe crear este método en AutorService
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró ningún autor llamado: " + nombre));
        return ResponseEntity.ok(autor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Autor> obtenerPorId(@PathVariable Long id) {
        Autor autor = autorService.obtenerPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró ningún autor con el ID: " + id));
        return ResponseEntity.ok(autor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Autor> actualizar(@PathVariable Long id, @RequestBody Autor autorActualizado) {
        Autor autorExistente = autorService.obtenerPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede actualizar. Autor no encontrado con ID: " + id));

        autorExistente.setNombre(autorActualizado.getNombre());

        return ResponseEntity.ok(autorService.guardar(autorExistente));
    }

    @PostMapping
    public ResponseEntity<Autor> crear(@RequestBody Autor autor) {
        Autor nuevoAutor = autorService.guardar(autor);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAutor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean eliminado = autorService.eliminar(id);
        if (!eliminado) {
            throw new ResourceNotFoundException("Error al eliminar: No se encontró ningún autor con el ID " + id);
        }
        return ResponseEntity.noContent().build();
    }
}
