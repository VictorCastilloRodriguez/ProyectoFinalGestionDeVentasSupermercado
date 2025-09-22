package com.example.ProyectoFinalGestionDeVentasSupermercado.controller;

import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.ProductoDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.service.EstadisticasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticasController {

    @Autowired
    private EstadisticasService estadisticasService;

    @GetMapping("/producto-mas-vendido")
    public ResponseEntity<ProductoDto> productoMasVendido() {
        return ResponseEntity.ok(estadisticasService.obtenerProductoMasVendido());
    }
}

