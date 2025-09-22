package com.example.ProyectoFinalGestionDeVentasSupermercado.controller;

import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.VentaDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.service.VentaService;
import com.fasterxml.jackson.databind.JsonNode;
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
    public ResponseEntity<VentaDto> registrarVenta(@RequestBody JsonNode payload) {
        if (payload == null || payload.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        VentaDto dto = ventaService.crearVentaDesdeJson(payload);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> anularVenta(@PathVariable Long id) {
        ventaService.anularVenta(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<?> obtenerVentasPorSucursalYFecha(
            @RequestParam Long sucursalId,
            @RequestParam(required = false) String fecha
    ) {
        LocalDate fechaConsulta = fecha != null ? LocalDate.parse(fecha) : LocalDate.now();
        return ResponseEntity.ok(ventaService.obtenerVentasPorSucursalYFecha(sucursalId, fechaConsulta));
    }
}

