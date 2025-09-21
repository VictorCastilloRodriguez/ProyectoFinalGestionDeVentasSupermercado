package com.example.ProyectoFinalGestionDeVentasSupermercado.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVentaCreacionDto {
    private Long productoId;
    private int cantidad;
}
