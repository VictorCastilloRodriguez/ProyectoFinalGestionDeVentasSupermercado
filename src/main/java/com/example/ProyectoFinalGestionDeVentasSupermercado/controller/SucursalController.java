package com.example.ProyectoFinalGestionDeVentasSupermercado.controller;

import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Sucursal;
import com.example.ProyectoFinalGestionDeVentasSupermercado.service.SucursalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    //Crear sucursal
    @PostMapping
    public ResponseEntity<Sucursal> crearSucursal(@Valid @RequestBody Sucursal sucursal) {
        Sucursal nuevaSucursal = sucursalService.crearSucursal(sucursal);
        return ResponseEntity.status(201).body(nuevaSucursal);
    }

    //Actualizar sucursal
    @PutMapping("/{id}")
    public ResponseEntity<Sucursal> actualizarSucursal(@PathVariable Long id, @Valid @RequestBody Sucursal sucursalActualizada) {
        Sucursal sucursalFinal = sucursalService.actualizarSucursal(id, sucursalActualizada);
        return ResponseEntity.ok(sucursalFinal);
    }

    // Eliminar sucursal
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSucursal(@PathVariable Long id) {
        sucursalService.eliminarSucursal(id);
        return ResponseEntity.noContent().build();
    }

    // Listar sucursales
    @GetMapping
    public ResponseEntity<Map<Long, Sucursal>> listarSucursales() {
        Map<Long, Sucursal> sucursalesMap = sucursalService.listarSucursales();
        return ResponseEntity.ok(sucursalesMap);
    }
}

