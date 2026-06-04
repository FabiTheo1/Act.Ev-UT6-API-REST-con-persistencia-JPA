package com.biblioteca.biblioteca_api.services;

import com.biblioteca.biblioteca_api.models.Socio;
import com.biblioteca.biblioteca_api.repositories.SocioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // Lombok: Genera el constructor para inyectar dependencias (los atributos 'final')
public class SocioService {
    private final SocioRepository socioRepository;

    public List<Socio> obtenerTodos() {
        return socioRepository.findAll();
    }

    public Optional<Socio> obtenerPorId(Long id) {
        return socioRepository.findById(id);
    }

    public Socio guardar(Socio socio) {
        return socioRepository.save(socio);
    }

    public boolean eliminar(Long id) {
        if (socioRepository.existsById(id)) {
            socioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
