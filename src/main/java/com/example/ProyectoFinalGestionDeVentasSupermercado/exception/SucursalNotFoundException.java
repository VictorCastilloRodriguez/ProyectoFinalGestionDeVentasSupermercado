package com.example.ProyectoFinalGestionDeVentasSupermercado.exception;

public class SucursalNotFoundException extends RuntimeException {

    private Long sucursalId;

    public SucursalNotFoundException(String message) {
        super(message);
    }

    public SucursalNotFoundException(Long sucursalId) {
        super("Sucursal con ID " + sucursalId + " no encontrada");
        this.sucursalId = sucursalId;
    }

    public Long getSucursalId() {
        return sucursalId;
    }
}
