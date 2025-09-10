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
    private String nombreProducto;
    private double precio;

   @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @OneToMany( mappedBy = "producto",cascade = CascadeType.ALL,orphanRemoval = true)
    @MapKeyJoinColumn(name = "sucursalId")
    private Map<Sucursal,Venta> ventaPorSucursal = new HashMap<>();

}
