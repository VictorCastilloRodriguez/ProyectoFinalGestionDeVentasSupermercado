package com.example.ProyectoFinalGestionDeVentasSupermercado.repository;

import com.example.ProyectoFinalGestionDeVentasSupermercado.model.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {

    @Query("SELECT dv.productoId, SUM(dv.cantidad) as total FROM DetalleVenta dv GROUP BY dv.productoId ORDER BY total DESC")
    List<Object[]> findProductoMasVendido();
}