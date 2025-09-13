package com.example.ProyectoFinalGestionDeVentasSupermercado.model;

import com.example.ProyectoFinalGestionDeVentasSupermercado.enums.Categoria;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del producto no puede estar vacio ")
    @Column(nullable = false)
    private String nombreProducto;

    @Min(value = 0, message = "EL precio debe ser mayor o igual a 0")
    @Column(nullable = false)
    private double precio;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
    private Categoria categoria;
}
