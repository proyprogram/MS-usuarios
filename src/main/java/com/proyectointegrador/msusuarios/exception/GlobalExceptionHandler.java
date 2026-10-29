package com.proyectointegrador.msusuarios.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> manejarIllegalArgument(IllegalArgumentException error) {
        return new ResponseEntity<>(error.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidaciones(MethodArgumentNotValidException error) {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", "Error de validación");
        String mensaje = error.getBindingResult().getFieldErrors().isEmpty()
                ? "Los datos enviados no son válidos."
                : error.getBindingResult().getFieldErrors().get(0).getField()
                        + ": " + error.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        respuesta.put("mensaje", mensaje);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }
}
