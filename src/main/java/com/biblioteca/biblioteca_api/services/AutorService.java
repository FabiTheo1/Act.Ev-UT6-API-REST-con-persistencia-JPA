package com.biblioteca.biblioteca_api.services;

import com.biblioteca.biblioteca_api.models.Autor;
import com.biblioteca.biblioteca_api.repositories.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // Lombok: Genera el constructor para inyectar dependencias (los atributos 'final')
public class AutorService {
    private final AutorRepository autorRepository;

    public List<Autor> obtenerTodos() {
        return autorRepository.findAll();
    }

    public Optional<Autor> obtenerPorNombre(String nombre) {
        return autorRepository.findByNombre(nombre);
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
}
