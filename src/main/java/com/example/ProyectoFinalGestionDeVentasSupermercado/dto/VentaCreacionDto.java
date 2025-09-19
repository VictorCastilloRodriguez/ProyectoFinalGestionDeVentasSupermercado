package com.example.ProyectoFinalGestionDeVentasSupermercado.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaCreacionDto {
    @NotNull(message = "El id de la sucursal no puede estar vacío")
    private Long idSucursal;

    @NotNull(message = "Debe incluir al menos un producto en la venta")
    @Size(min = 1, message = "Debe haber al menos un producto en la venta")
    private List<DetalleVentaCreacionDto> detalleVentaDtos;
}

