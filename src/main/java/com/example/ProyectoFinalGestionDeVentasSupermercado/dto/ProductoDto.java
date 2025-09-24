package com.example.ProyectoFinalGestionDeVentasSupermercado.dto;

import com.example.ProyectoFinalGestionDeVentasSupermercado.enums.Categoria;
import lombok.Data;

@Data
public class ProductoDto {
    private Long id;
    private String nombreProducto;
    private Double precioProducto;
    private Integer stockProducto;
    private Categoria categoriaProducto;
}