package com.example.ProyectoFinalGestionDeVentasSupermercado.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.SpringVersion;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductoMasVendidoDto {
    private Long productoId;
    private String nombreProducto;
    private Integer cantidadVendida;
}
