package com.biblioteca.biblioteca_api.repositories;

import com.biblioteca.biblioteca_api.models.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SocioRepository extends JpaRepository<Socio, Long> {
    
    @Query("SELECT COUNT(l) FROM Socio s JOIN s.librosPrestados l WHERE s.id = :socioId")
    Long contarLibrosPrestadosPorSocio(@Param("socioId") Long socioId);
}