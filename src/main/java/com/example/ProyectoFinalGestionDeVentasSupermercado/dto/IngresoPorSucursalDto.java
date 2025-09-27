package com.example.ProyectoFinalGestionDeVentasSupermercado.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngresoPorSucursalDto {
    private Long sucursalId;
    private  String nombreSucursal;
    private Double totaIngresos;
}
