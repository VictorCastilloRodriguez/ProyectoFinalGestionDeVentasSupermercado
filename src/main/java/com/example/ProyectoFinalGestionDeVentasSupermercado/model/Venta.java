package com.example.ProyectoFinalGestionDeVentasSupermercado.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int cantidad;
    private LocalDateTime fechaVenta;
    private boolean eliminado = false;

    @ManyToOne
    @JoinColumn(name = "productoId", nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "sucursalId", nullable = false)
    private Sucursal sucursal;
}
