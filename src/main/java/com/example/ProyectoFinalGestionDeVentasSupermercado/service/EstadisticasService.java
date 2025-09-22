package com.example.ProyectoFinalGestionDeVentasSupermercado.service;

import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.ProductoDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.exception.ProductoNotFoundException;
import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Producto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.DetalleVentaRepository;
import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


    @Service
    public class EstadisticasService {

        @Autowired
        private DetalleVentaRepository detalleVentaRepository;

        @Autowired
        private ProductoRepository productoRepository;

        public ProductoDto obtenerProductoMasVendido() {
            List<Object[]> resultados = detalleVentaRepository.findProductoMasVendido();
            if (resultados.isEmpty()) {
                throw new RuntimeException("No hay ventas registradas");
            }

            Long productoId = (Long) resultados.get(0)[0];
            Producto producto = productoRepository.findById(productoId)
                    .orElseThrow(() -> new ProductoNotFoundException(productoId));

            ProductoDto dto = new ProductoDto();
            dto.setNombreProducto(producto.getNombreProducto());
            dto.setPrecio(producto.getPrecio());
            dto.setCategoria(producto.getCategoria());
            dto.setStock(producto.getStock());
            return dto;
        }
    }