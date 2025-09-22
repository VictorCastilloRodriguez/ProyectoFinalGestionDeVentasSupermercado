package com.example.ProyectoFinalGestionDeVentasSupermercado.controller;

import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.VentaCreacionDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.VentaDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
 public ResponseEntity<VentaDto> crearVenta(@Valid @RequestBody VentaCreacionDto ventaDto) {
        VentaDto nuevaVenta = ventaService.crearVenta(ventaDto);
        return ResponseEntity.status(201).body(nuevaVenta);
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
