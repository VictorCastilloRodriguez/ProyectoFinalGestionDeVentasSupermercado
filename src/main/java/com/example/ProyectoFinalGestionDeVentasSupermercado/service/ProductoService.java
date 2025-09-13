package com.example.ProyectoFinalGestionDeVentasSupermercado.service;

import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Producto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ProyectoFinalGestionDeVentasSupermercado.exception.ProductoNotFoundException;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    // Crear producto
    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    // Actualizar producto
    public Producto actualizarProducto(Long id, Producto productoActualizado) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto con id: " + id + "no encontrado"));

        producto.setNombreProducto(productoActualizado.getNombreProducto());
        producto.setPrecio(productoActualizado.getPrecio());
        producto.setCategoria(productoActualizado.getCategoria());
        return productoRepository.save(producto);
    }

    // Eliminar producto
    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ProductoNotFoundException("Producto con id: " + id + " no encontrado");
        }
        productoRepository.deleteById(id);
    }

    // Listar productos
    public Map<Long, Producto> listarProductos() {
        return productoRepository.findAll().stream()
                .collect(Collectors.toMap(Producto::getId, producto -> producto));
    }
}
