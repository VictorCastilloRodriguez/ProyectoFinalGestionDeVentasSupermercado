package com.proyectofinal.gestionventas.supermercado.controller;

import com.proyectofinal.gestionventas.supermercado.dto.VentaDto;
import com.proyectofinal.gestionventas.supermercado.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<VentaDto> crearVenta(@RequestBody VentaDto ventaDto) {
        VentaDto nuevaVenta = ventaService.crearVenta(ventaDto);
        return new ResponseEntity<>(nuevaVenta, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<VentaDto>> listarVentas() {
        List<VentaDto> ventas = ventaService.listarVentas();
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }
}
