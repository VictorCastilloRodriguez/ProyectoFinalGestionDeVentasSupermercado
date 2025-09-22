package com.example.ProyectoFinalGestionDeVentasSupermercado.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SucursalDto {

    @NotBlank(message = "El nombre de la sucursal no puede estar vacío")
    private String nombreSucursal;

    @NotBlank(message = "La dirección de la sucursal no puede estar vacía")
    private String direccionSucursal;
}
