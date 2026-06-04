package com.biblioteca.biblioteca_api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDetalles {
    private LocalDateTime marcaDeTiempo;
    private String mensaje;
    private String detalles;
}