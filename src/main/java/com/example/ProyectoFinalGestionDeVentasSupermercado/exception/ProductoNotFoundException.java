package com.example.ProyectoFinalGestionDeVentasSupermercado.exception;

public class ProductoNotFoundException extends RuntimeException {

    private Long productoId;

    public ProductoNotFoundException(String message) {
        super(message);
    }

    public ProductoNotFoundException(Long productoId) {
        super("Producto con ID " + productoId + " no encontrado");
        this.productoId = productoId;
    }

    public Long getProductoId() {
        return productoId;
    }
}
