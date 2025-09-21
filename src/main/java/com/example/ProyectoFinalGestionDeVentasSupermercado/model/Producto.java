package com.example.ProyectoFinalGestionDeVentasSupermercado.model;

import com.example.ProyectoFinalGestionDeVentasSupermercado.enums.Categoria;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String nombre;

    @Min(value = 0, message = "EL precio debe ser mayor o igual a 0")
    @Column(nullable = false)
    private double precio;

    @Min(value = 0, message = "EL stock debe ser mayor o igual a 0")
    @Column(nullable = false)
    private Integer stock;


   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
    private Categoria categoria;

    @Column(nullable = false)
    private boolean retirado=false;

    //CONSTRUCTOR SIN RETIRADO
    public Producto(String nombre, double precio, Integer stock, Categoria categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }









}
