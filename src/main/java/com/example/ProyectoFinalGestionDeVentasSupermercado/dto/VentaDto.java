package com.example.ProyectoFinalGestionDeVentasSupermercado.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class VentaDto {
    private Long id;
    private LocalDate fechaVenta;
    private boolean eliminado;
    private Long sucursalId;
    private Double importeTotal;
    private SucursalDto sucursal;
    private List<DetalleVentaDto> detalles;
}
