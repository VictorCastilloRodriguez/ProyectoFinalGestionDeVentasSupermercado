package com.example.ProyectoFinalGestionDeVentasSupermercado.controller;

import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.SucursalDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.service.SucursalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @PostMapping
    public ResponseEntity<SucursalDto> crearSucursal(@Valid @RequestBody SucursalDto sucursalDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sucursalService.crearSucursal(sucursalDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SucursalDto> actualizarSucursal(@PathVariable("id") Long id, @Valid @RequestBody SucursalDto sucursalDto) {
        return ResponseEntity.status(200).body(sucursalService.actualizarSucursal(id, sucursalDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarSucursal(@PathVariable("id") Long id) {
        sucursalService.eliminarSucursal(id);
        return ResponseEntity.status(HttpStatus.OK).body("La sucursal ha sido eliminada");
    }

    @GetMapping
    public ResponseEntity<Map<Long, SucursalDto>> listarSucursales() {
        return ResponseEntity.ok(sucursalService.listarSucursales());
    }
}


