package com.example.ProyectoFinalGestionDeVentasSupermercado.dto;

import com.example.ProyectoFinalGestionDeVentasSupermercado.enums.Categoria;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductoDto {

    @NotBlank(message = "El nombre del producto no puede estar vacio")
    @Column(nullable = false)
    private String nombreProducto;

    @NotNull(message = "El precio del producto no puede estar vacio")
    @Min(value = 0, message = "El precio debe ser mayor o igual a 0")
    @Column(nullable = false)
    private Double precioProducto;

    @NotNull(message = "El stock del producto no puede estar vacio")
    @Min(value = 0, message = "El stock debe ser mayor o igual a 0")
    @Column(nullable = false)
    private Integer stockProducto;

    @Column(nullable = false)
    private Categoria categoriaProducto;
}