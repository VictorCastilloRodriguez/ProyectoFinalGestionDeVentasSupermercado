package com.example.ProyectoFinalGestionDeVentasSupermercado.controller;

import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.VentaCreacionDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.VentaDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.service.VentaService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PostMapping
    public ResponseEntity<VentaDto> registrarVenta(@Valid @RequestBody VentaCreacionDto dto) {

        VentaDto respuesta = ventaService.crearVentaDesdeDto(dto);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> anularVenta(@PathVariable Long id) {
        ventaService.anularVenta(id);
        return ResponseEntity.ok("Venta eliminada correctamente.");
    }

    @GetMapping
    public ResponseEntity<?> obtenerVentasPorSucursalYFecha(
            @RequestParam(required = false) Long sucursalId,
            @RequestParam(required = false) String fecha
    ) {
        LocalDate fechaConsulta = fecha != null ? LocalDate.parse(fecha) : null;
        return ResponseEntity.ok(ventaService.obtenerVentasPorSucursalYFecha(sucursalId, fechaConsulta));
    }
}

