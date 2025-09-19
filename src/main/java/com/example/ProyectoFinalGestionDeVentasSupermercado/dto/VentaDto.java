package com.example.ProyectoFinalGestionDeVentasSupermercado.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaDto {
    private Long idVenta;
    private Long idSucursal;
    private LocalDate fecha;
    private List<DetalleVentaDto> detalleVentaDtos;
    private Double totalVenta;
}

