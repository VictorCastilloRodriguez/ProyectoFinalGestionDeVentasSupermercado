package com.example.ProyectoFinalGestionDeVentasSupermercado.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "sucursales")

public class Sucursal {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String direccion;

    @OneToMany(mappedBy = "sucursal")
    private List<Venta> ventas;
}
