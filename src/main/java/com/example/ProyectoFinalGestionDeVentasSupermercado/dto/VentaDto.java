package com.example.ProyectoFinalGestionDeVentasSupermercado.dto;

import com.example.ProyectoFinalGestionDeVentasSupermercado.model.DetalleVenta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class VentaDto {

    private Long idVenta;
    private Long idSucursal;
    private LocalDate fecha;
    private List<DetalleVentaDto> detalleVentaDtos;

}
