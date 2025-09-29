package com.example.ProyectoFinalGestionDeVentasSupermercado.service;

import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.ProductoDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.exception.ProductoNotFoundException;
import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Producto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.ProductoRepository;
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

    public ProductoDto save(ProductoDto productoDto) {
        return entityToDto(productoRepository.save(dtoToEntity(productoDto)));
    }

    public Map<Long, ProductoDto> findAll() {
        return productoRepository.findAll().stream()
                .filter(producto -> !producto.isRetirado())
                .collect(Collectors.toMap(
                        Producto::getId,
                        this::entityToDto
                ));
    }

    public ProductoDto actualizarProducto(Long id, @Valid ProductoDto productoActualizado) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto con id: " + id + " no encontrado"));

        if (producto.isRetirado()){
            throw new ProductoNotFoundException("Producto con id: " + id + " fue retirado anteriormente.");

        }
        producto.setNombre(productoActualizado.getNombreProducto());
        producto.setPrecio(productoActualizado.getPrecioProducto());
        producto.setStock(productoActualizado.getStockProducto());
        producto.setCategoria(productoActualizado.getCategoriaProducto());

        return entityToDto(productoRepository.save(producto));
    }

    public void eliminarProducto(Long id) {
        Producto productoEliminado = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto con id: " + id + " no encontrado"));

        if (productoEliminado.isRetirado()) {
            throw new ProductoNotFoundException("Producto con id: " + id + " ya está retirado");
        }
        productoEliminado.setRetirado(true);
        productoEliminado.setStock(0);
        productoRepository.save(productoEliminado);
    }

    private Producto dtoToEntity(ProductoDto dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombreProducto());
        producto.setPrecio(dto.getPrecioProducto());
        producto.setStock(dto.getStockProducto());
        producto.setCategoria(dto.getCategoriaProducto());
        return producto;
    }

    private ProductoDto entityToDto(Producto producto) {
        ProductoDto dto = new ProductoDto();
        dto.setNombreProducto(producto.getNombre());
        dto.setPrecioProducto(producto.getPrecio());
        dto.setCategoriaProducto(producto.getCategoria());
        dto.setStockProducto(producto.getStock());
        return dto;
    }
}