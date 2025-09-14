package com.example.ProyectoFinalGestionDeVentasSupermercado.repository;

import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Venta;
import jdk.jshell.Snippet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findBySucursal(Long sucursalId);
    List<Venta> findByFecha(LocalDate fecha);
    List<Venta> findBySucursalIdAndFecha(Long sucursalId, LocalDate fecha);
}
