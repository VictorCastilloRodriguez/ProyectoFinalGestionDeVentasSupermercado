package com.example.ProyectoFinalGestionDeVentasSupermercado.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VentaDto {
    @NotNull(message = "El id de la venta no puede estar vacía")
    private Long idVenta;
    @NotNull(message = "El id de la sucursal no puede estar vacía")
    private Long idSucursal;
    private LocalDate fecha;
    private List<DetalleVentaDto> detalleVentaDtos;
}

