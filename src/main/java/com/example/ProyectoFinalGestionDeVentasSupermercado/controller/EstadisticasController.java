package com.example.ProyectoFinalGestionDeVentasSupermercado.controller;

import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.IngresoPorSucursalDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.ProductoDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.ProductoMasVendidoDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.service.EstadisticasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticasController {

    @Autowired
    private EstadisticasService estadisticasService;

    @GetMapping("/producto-mas-vendido")
    public ResponseEntity<ProductoDto> productoMasVendido() {
        return ResponseEntity.ok(estadisticasService.obtenerProductoMasVendido());
    }

    @GetMapping("/total-ingresos")
    public ResponseEntity<String> getTotalIngresosVenta() {
        Double total = estadisticasService.obtenerTotalIngresos();
        return ResponseEntity.ok("El total de ingresos del supermercado es: " + total + "€.");
    }

    @GetMapping("/productos-mas-vendidos")
    public ResponseEntity<List<ProductoMasVendidoDto>> getProductosMasVendidos() {
        return ResponseEntity.ok(estadisticasService.listarProductosMasVendidos());
    }

    @GetMapping("/ingresos-por-sucursal")
    public ResponseEntity<List<IngresoPorSucursalDto>> getIngresosPorSucursal() {
        return ResponseEntity.ok(estadisticasService.obtenerTotalIngresosPorSucursal());
    }
}

