package com.example.ProyectoFinalGestionDeVentasSupermercado.repository;

import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findBySucursalId(Long sucursalId);
    List<Venta> findByFechaVenta(LocalDate fechaVenta);
    List<Venta> findBySucursalIdAndFechaVentaAndEliminadoFalse(Long sucursalId, LocalDate fechaVenta);
    List<Venta> findBySucursalIdAndEliminadoFalse(Long sucursalId);
}
