package com.example.ProyectoFinalGestionDeVentasSupermercado.service;

import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.ProductoDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Producto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Sucursal;
import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.ProductoRepository;
import com.example.ProyectoFinalGestionDeVentasSupermercado.exception.ProductoNotFoundException;

import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.SucursalRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    // Crear producto
    public ProductoDto crearProducto(@Valid ProductoDto productoDto) {
        Producto producto = dtoToEntity(productoDto);
        Producto guardado = productoRepository.save(producto);
        return entityToDto(guardado);
    }

    // Actualizar producto
    public ProductoDto actualizarProducto(Long id, @Valid ProductoDto productoActualizado) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto con id: " + id + " no encontrado"));

        producto.setNombreProducto(productoActualizado.getNombreProducto());
        producto.setPrecio(productoActualizado.getPrecio());
        producto.setStock(productoActualizado.getStock());
        producto.setCategoria(productoActualizado.getCategoria());

        Producto actualizado = productoRepository.save(producto);
        return entityToDto(actualizado);
    }

    // Eliminar producto
    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ProductoNotFoundException("Producto con id: " + id + " no encontrado");
        }
        productoRepository.deleteById(id);
    }

    // Listar productos
    public Map<Long, ProductoDto> listarProductos() {
        return productoRepository.findAll().stream()
                .collect(Collectors.toMap(
                        Producto::getId,
                        this::entityToDto
                ));
    }

    // Conversión de DTO a entidad
    private Producto dtoToEntity(ProductoDto dto) {
        Producto producto = new Producto();
        producto.setNombreProducto(dto.getNombreProducto());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setCategoria(dto.getCategoria());

        return producto;
    }

    // Conversión de entidad a DTO
    private ProductoDto entityToDto(Producto producto) {
        ProductoDto dto = new ProductoDto();
        dto.setNombreProducto(producto.getNombreProducto());
        dto.setPrecio(producto.getPrecio());
        dto.setCategoria(producto.getCategoria());
        dto.setStock(producto.getStock());


        return dto;
    }
}