package com.example.ProyectoFinalGestionDeVentasSupermercado.dto;

import lombok.Data;

@Data
public class DetalleVentaDto {
    private Long productoId;
    private String nombreProducto;
    private Double precio;
    private Integer cantidad;
}