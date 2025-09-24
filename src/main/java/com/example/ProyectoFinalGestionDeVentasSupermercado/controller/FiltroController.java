package com.example.ProyectoFinalGestionDeVentasSupermercado.controller;

import com.example.ProyectoFinalGestionDeVentasSupermercado.service.FiltroVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/filtros")
public class FiltroController {

    @Autowired
    private FiltroVentaService filtroVentaService;


    @GetMapping("/total-ingresos")
    public ResponseEntity<Double> getTotalIngresosVenta() {
        return ResponseEntity.ok(filtroVentaService.obtenerTotalIngresos());
    }

    @GetMapping("/productos-mas-vendidos")
    public ResponseEntity<List<Object[]>> getProductosMasVendidos() {
        return ResponseEntity.ok(filtroVentaService.listarProductosMasVendidos());
    }


    @GetMapping("/ingresos-por-venta")
    public ResponseEntity<List<Object[]>> getIngresosPorVenta() {
        return ResponseEntity.ok(filtroVentaService.obtenerTotalIngresosPorVenta());
    }

    @GetMapping("/ingresos-por-sucursal")
    public ResponseEntity<List<Object[]>> getIngresosPorSucursal() {
        return ResponseEntity.ok(filtroVentaService.obtenerTotalIngresosPorSucursal());
    }
}
