package com.example.ProyectoFinalGestionDeVentasSupermercado.controller;

import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Producto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.service.ProductoService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    //Crear producto
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@Valid @RequestBody Producto producto) {
        Producto nuevoProducto = productoService.crearProducto(producto);
        return ResponseEntity.status(201).body(nuevoProducto);
    }

    //Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @Valid @RequestBody Producto productoActualizado) {
        Producto productoActualizadoFinal = productoService.actualizarProducto(id, productoActualizado);
        return ResponseEntity.ok(productoActualizadoFinal);
    }

// Eliminar producto
@DeleteMapping("/{id}")
public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
    productoService.eliminarProducto(id);
    return ResponseEntity.noContent().build();
}

    // Listar productos
    @GetMapping
    public ResponseEntity<Map<Long, Producto>> listarProductosComoMapa() {
        Map<Long, Producto> productosMap = productoService.listarProductosComoMapa();
        return ResponseEntity.ok(productosMap);
    }
}
