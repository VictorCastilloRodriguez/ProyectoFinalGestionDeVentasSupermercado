package com.example.ProyectoFinalGestionDeVentasSupermercado.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ManejoGlobalDeErrores {

    @ExceptionHandler(ProductoNotFoundException.class)
    public ResponseEntity<String> manejarProductoNoEncontrado(ProductoNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(SucursalNotFoundException.class)
    public ResponseEntity<String> manejarSucursalNoEncontrada(SucursalNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> manejarErroresGenericos(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<Map<String, String>>>> manejarErrores(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errores = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> Map.of(
                        "campo", error.getField(),
                        "mensaje", error.getDefaultMessage()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(Map.of("errores", errores));
    }
}