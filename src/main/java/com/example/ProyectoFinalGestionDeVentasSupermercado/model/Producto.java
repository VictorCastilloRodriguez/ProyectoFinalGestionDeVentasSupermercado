package com.example.ProyectoFinalGestionDeVentasSupermercado.model;

import com.example.ProyectoFinalGestionDeVentasSupermercado.enums.Categoria;
import jakarta.persistence.*;
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
    @Column(nullable = false)
    private String nombreProducto;
    @Column(nullable = false)
    private double precio;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
    private Categoria categoria;
}
