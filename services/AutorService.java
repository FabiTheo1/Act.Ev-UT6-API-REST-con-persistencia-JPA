package com.biblioteca.biblioteca_api.services;

import com.biblioteca.biblioteca_api.exceptions.ResourceNotFoundException;
import com.biblioteca.biblioteca_api.models.Autor;
import com.biblioteca.biblioteca_api.repositories.AutorRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // Lombok: Genera el constructor para inyectar dependencias (los atributos 'final')
public class AutorService {
    private final AutorRepository autorRepository;

    public List<Autor> obtenerTodos() {
        return autorRepository.findAll();
    }

    public Optional<Autor> obtenerPorId(Long id) {
        return autorRepository.findById(id);
    }

    public Autor guardar(Autor autor) {
        return autorRepository.save(autor);
    }

    public boolean eliminar(Long id) {
        if (autorRepository.existsById(id)) {
            autorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @GetMapping("/buscar")
    public ResponseEntity<Autor> buscarPorNombre(@RequestParam(required = false) String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Autor autor = autorRepository.findByNombre(nombre)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró ningún autor llamado: " + nombre));
        return ResponseEntity.ok(autor);
    }
}
