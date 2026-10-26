
package com.proyectointegrador.msusuarios.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Map<String, Object>> crearRespuesta(
            HttpStatus estado, String mensaje) {

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("fecha", LocalDateTime.now());
        respuesta.put("estado", estado.value());
        respuesta.put("error", estado.getReasonPhrase());
        respuesta.put("mensaje", mensaje);

        return ResponseEntity.status(estado).body(respuesta);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> manejarIllegalArgument(
            IllegalArgumentException error) {

        return crearRespuesta(
                HttpStatus.BAD_REQUEST,
                error.getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarValidaciones(
            MethodArgumentNotValidException error) {

        String mensaje = error.getBindingResult().getFieldErrors().isEmpty()
                ? "Los datos enviados no son válidos."
                : error.getBindingResult().getFieldErrors()
                .get(0).getDefaultMessage();

        return crearRespuesta(HttpStatus.BAD_REQUEST, mensaje);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> manejarErrorGeneral(
            Exception error) {

        return crearRespuesta(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrió un error inesperado."
        );
    }
}

