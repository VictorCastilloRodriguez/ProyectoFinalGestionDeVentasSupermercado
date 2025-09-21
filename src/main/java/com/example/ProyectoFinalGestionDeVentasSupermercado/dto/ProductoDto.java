package com.example.ProyectoFinalGestionDeVentasSupermercado.dto;

import com.example.ProyectoFinalGestionDeVentasSupermercado.enums.Categoria;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDto {



    @NotBlank(message = "El nombre del producto no puede estar vacío")
    private String nombreProducto;

    @Min(value = 0, message = "El precio debe ser mayor o igual a 0")
    private double precioProducto;

    @NotNull(message = "El stock no puede estar vacío")
    @Min(value = 0, message = "El stock debe ser mayor o igual a 0")
    private Integer stockProducto;


    @NotNull(message = "La categoría es obligatoria")
    private Categoria categoriaProducto;
}

