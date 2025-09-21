package com.example.ProyectoFinalGestionDeVentasSupermercado.controller;

import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.VentaCreacionDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.VentaDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    //Registrar nueva venta
    @PostMapping
    public ResponseEntity<VentaDto> crearVenta(@RequestBody VentaCreacionDto dto) {
        VentaDto venta = ventaService.crearVenta(dto);
        return ResponseEntity.ok(venta);
    }

    // Consultar ventas por sucursal y fecha
    @GetMapping
    public ResponseEntity<List<VentaDto>> obtenerVentasPorSucursalYFecha(
            @RequestParam Long sucursalId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<VentaDto> ventas = ventaService.obtenerVentasPorSucursalYFecha(sucursalId, fecha);
        return ResponseEntity.ok(ventas);
    }

    // Borrado lógico de venta
    @DeleteMapping("/{id}")
    public ResponseEntity<String> anularVenta(@PathVariable Long id) {
        ventaService.anularVenta(id);
        return ResponseEntity.ok("Venta anulada correctamente");
    }
}

