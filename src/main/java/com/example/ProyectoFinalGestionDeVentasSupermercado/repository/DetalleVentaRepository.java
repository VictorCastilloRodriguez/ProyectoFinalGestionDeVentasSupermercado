package com.example.ProyectoFinalGestionDeVentasSupermercado.repository;

import com.example.ProyectoFinalGestionDeVentasSupermercado.model.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {


    @Query("SELECT SUM(v.cantidad * v.precio) FROM DetalleVenta v WHERE v.venta.eliminado = false")
    Double totalIngresosVenta();

    @Query("SELECT v.productoId, SUM(v.cantidad) as totalUnidades FROM DetalleVenta v GROUP BY v.productoId ORDER BY totalUnidades DESC")
    List<Object[]> findProductosMasVendidos();

    @Query("SELECT v.venta.id, SUM(v.precio * v.cantidad) FROM DetalleVenta v GROUP BY v.venta.id")
    List<Object[]> totalIngresosPorVenta();

    @Query("SELECT v.venta.sucursal.id, SUM(v.precio * v.cantidad) FROM DetalleVenta v WHERE v.venta.eliminado = false GROUP BY v.venta.sucursal.id")
    List<Object[]> totalIngresosPorSucursal();

    @Query("SELECT dv.productoId, SUM(dv.cantidad) as total FROM DetalleVenta dv GROUP BY dv.productoId ORDER BY total DESC")
    List<Object[]> findProductoMasVendido();
}

