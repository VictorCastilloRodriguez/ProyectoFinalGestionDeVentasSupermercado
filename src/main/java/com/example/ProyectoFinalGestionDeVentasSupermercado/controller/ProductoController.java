package com.example.ProyectoFinalGestionDeVentasSupermercado.controller;

import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.ProductoDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Producto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.service.ProductoService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // Crear producto
    @PostMapping
    public ResponseEntity<ProductoDto> crearProducto(@Valid @RequestBody ProductoDto  productoDto) {

        Producto producto = new Producto(null,productoDto.getNombreProducto(), productoDto.getPrecioProducto(), productoDto.getStockProducto(), productoDto.getCategoriaProducto(),false);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.save(producto));
    }

    // Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDto> actualizarProducto(@PathVariable("id") Long id, @Valid @RequestBody ProductoDto productoDto) {
        return ResponseEntity.status(200).body(productoService.actualizarProducto(id, productoDto));
    }


    // Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable("id") Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.status(HttpStatus.OK).body("EL producto ha sido eliminado");
    }

    // Listar productos
    @GetMapping
    public ResponseEntity<Map<Long,ProductoDto>> listarProductos() {
        return ResponseEntity.ok(productoService.findAll());
    }
}


