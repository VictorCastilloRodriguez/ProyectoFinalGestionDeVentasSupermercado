package com.example.ProyectoFinalGestionDeVentasSupermercado.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DetalleVentaDto {
    private Long productoId;
    private int cantidad;
    private double importe;
}

