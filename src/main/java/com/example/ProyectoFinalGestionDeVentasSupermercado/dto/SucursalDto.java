package com.example.ProyectoFinalGestionDeVentasSupermercado.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SucursalDto {
    @NotBlank(message = "El nombre de la sucursal no puede estar vacia")
    private String nombreSucursal;
    @NotBlank(message = "La direccion de la sucursal no puede estar vacia")
    private String direccionSucursal;
}
