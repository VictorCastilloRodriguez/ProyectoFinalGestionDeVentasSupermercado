package com.example.ProyectoFinalGestionDeVentasSupermercado.controller;

import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.IngresoPorSucursalDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.ProductoMasVendidoDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.service.FiltroVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/estadisticas")
public class FiltroController {

    @Autowired
    private FiltroVentaService filtroVentaService;


    @GetMapping("/total-ingresos")
    public ResponseEntity<String> getTotalIngresosVenta() {
        Double total = filtroVentaService.obtenerTotalIngresos();
        return ResponseEntity.ok("El total de ingresos del supermercado es: " + total + "€.");
    }

    @GetMapping("/productos-mas-vendidos")
    public ResponseEntity<List<ProductoMasVendidoDto>> getProductosMasVendidos() {
        return ResponseEntity.ok(filtroVentaService.listarProductosMasVendidos());
    }

    @GetMapping("/ingresos-por-sucursal")
    public ResponseEntity<List<IngresoPorSucursalDto>> getIngresosPorSucursal() {
        return ResponseEntity.ok(filtroVentaService.obtenerTotalIngresosPorSucursal());
    }
}
