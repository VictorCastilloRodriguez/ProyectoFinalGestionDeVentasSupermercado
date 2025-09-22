package com.example.ProyectoFinalGestionDeVentasSupermercado.repository;

import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    // Ventas por sucursal (todas)
    List<Venta> findBySucursalId(Long sucursalId);

    // Ventas por fecha exacta (incluye hora, cuidado)
    List<Venta> findByFechaVenta(LocalDate fechaVenta);

    // Ventas por sucursal y fecha (usando solo la fecha, sin hora)
    @Query("SELECT v FROM Venta v WHERE v.sucursal.id = :sucursalId AND DATE(v.fechaVenta) = :fecha AND v.eliminado = false")
    List<Venta> findBySucursalAndFecha(@Param("sucursalId") Long sucursalId, @Param("fecha") LocalDate fecha);

    // Ventas activas por sucursal
    List<Venta> findBySucursalIdAndEliminadoFalse(Long sucursalId);
}
