package com.example.ProyectoFinalGestionDeVentasSupermercado.repository;

import com.example.ProyectoFinalGestionDeVentasSupermercado.model.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {

    @Query("SELECT SUM(v.importe) FROM VentaDetalleDto v")
    Double totalIngresosVenta();

    @Query("SELECT v.producto, SUM(v.unidades) as totalUnidades" +
           "FROM VentaDetalleDto v" +
           "GROUP BY v.producto" +
           "ORDER BY totalUnidades DESC")
    List<Object[]> findProductosMasVendidos();


    @Query("SELECT v.venta.id, SUM(v.importe) FROM VentaDetalleDto v GROUP BY v.venta.id")
    List<Object[]> totalIngresosPorVenta();


    @Query("SELECT v.sucursal.id, SUM(v.importe) FROM VentaDetalleDto v GROUP BY v.sucursal.id")
    List<Object[]> totalIngresosPorSucursal();


}
