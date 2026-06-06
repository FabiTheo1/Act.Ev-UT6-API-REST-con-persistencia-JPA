package com.biblioteca.biblioteca_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Maneja nuestra excepción personalizada 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetalles> manejarResourceNotFoundException(
            ResourceNotFoundException exception, WebRequest webRequest) {
        
        ErrorDetalles errorDetalles = new ErrorDetalles(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );
        
        return new ResponseEntity<>(errorDetalles, HttpStatus.NOT_FOUND);
    }

    // Maneja cualquier otro error inesperado 500 para que la API no se caiga
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetalles> manejarExcepcionesGlobales(
            Exception exception, WebRequest webRequest) {
        
        ErrorDetalles errorDetalles = new ErrorDetalles(
                LocalDateTime.now(),
                "Ha ocurrido un error interno en el servidor",
                webRequest.getDescription(false)
        );
        
        return new ResponseEntity<>(errorDetalles, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}