package com.example.ProyectoFinalGestionDeVentasSupermercado.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "El nombre de la sucursal no puede estar vacia ")
    @Column(nullable = false)
    private String nombreSucursal;

    @NotBlank(message = "La direccion de la sucursal no puede estar vacia ")
    @Column(nullable = false)
    private String direccion;

    @OneToMany(mappedBy = "sucursal")
    private List<Venta> ventas;
}
