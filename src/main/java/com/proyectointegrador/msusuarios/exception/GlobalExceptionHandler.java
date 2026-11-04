package com.proyectointegrador.msusuarios.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<Map<String, Object>> manejarAccesoDenegado(
            SecurityException error) {

        return crearRespuesta(HttpStatus.FORBIDDEN, error.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> manejarCuerpoInvalido(
            HttpMessageNotReadableException error) {

        return crearRespuesta(
                HttpStatus.BAD_REQUEST,
                "El cuerpo de la petición no es válido. Revisa los tipos de datos (el precio debe ser un número entero)."
        );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, Object>> manejarParametroFaltante(
            MissingServletRequestParameterException error) {

        return crearRespuesta(
                HttpStatus.BAD_REQUEST,
                "Falta el parámetro obligatorio: " + error.getParameterName()
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> manejarTipoInvalido(
            MethodArgumentTypeMismatchException error) {

        return crearRespuesta(
                HttpStatus.BAD_REQUEST,
                "El valor del parámetro '" + error.getName() + "' no es válido."
        );
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
