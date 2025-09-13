package com.example.ProyectoFinalGestionDeVentasSupermercado.exception;

public class ProductoNotFoundException extends RuntimeException {
    public ProductoNotFoundException(String message) {
        super(message);
    }
}
