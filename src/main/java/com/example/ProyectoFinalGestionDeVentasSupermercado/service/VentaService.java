package com.example.ProyectoFinalGestionDeVentasSupermercado.service;

import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.*;
import com.example.ProyectoFinalGestionDeVentasSupermercado.exception.ProductoNotFoundException;
import com.example.ProyectoFinalGestionDeVentasSupermercado.exception.SucursalNotFoundException;
import com.example.ProyectoFinalGestionDeVentasSupermercado.model.*;
import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private ProductoRepository productoRepository;

    // Crear venta con validación y actualización de stock
    public VentaDto crearVenta(VentaCreacionDto dto) {
        Sucursal sucursal = sucursalRepository.findById(dto.getIdSucursal())
                .orElseThrow(() -> new SucursalNotFoundException(dto.getIdSucursal()));

        Venta venta = new Venta();
        venta.setSucursal(sucursal);
        venta.setFechaVenta(LocalDateTime.now());
        venta.setEliminado(false);

        List<DetalleVenta> detalles = dto.getDetalleVentaDtos().stream()
                .map(detDto -> {
                    Producto producto = productoRepository.findById(detDto.getProductoId())
                            .orElseThrow(() -> new ProductoNotFoundException(detDto.getProductoId()));

                    if (producto.getStock() < detDto.getCantidad()) {
                        throw new RuntimeException("Stock insuficiente para el producto con ID " + producto.getId());
                    }

                    producto.setStock(producto.getStock() - detDto.getCantidad());
                    productoRepository.save(producto);

                    DetalleVenta detalle = new DetalleVenta();
                    detalle.setProducto(producto);
                    detalle.setCantidad(detDto.getCantidad());
                    detalle.setImporte(producto.getPrecio() * detDto.getCantidad());
                    detalle.setVenta(venta);
                    return detalle;
                })
                .collect(Collectors.toList());

        venta.setDetallesVentas(detalles);
        Venta guardada = ventaRepository.save(venta);

        return convertirVentaDto(guardada);
    }

    // Convertir entidad Venta a DTO
    public VentaDto convertirVentaDto(Venta venta) {
        VentaDto dto = new VentaDto();
        dto.setIdVenta(venta.getId());
        dto.setIdSucursal(venta.getSucursal().getId());
        dto.setFecha(venta.getFechaVenta().toLocalDate());

        List<DetalleVentaDto> detallesDto = venta.getDetallesVentas().stream()
                .map(det -> new DetalleVentaDto(
                        det.getProducto().getId(),
                        det.getCantidad(),
                        det.getImporte()
                ))
                .collect(Collectors.toList());

        dto.setDetalleVentaDtos(detallesDto);
        dto.setTotalVenta(detallesDto.stream().mapToDouble(DetalleVentaDto::getImporte).sum());

        return dto;
    }

    // Borrado lógico de venta
    public void anularVenta(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta con ID " + id + " no encontrada"));
        venta.setEliminado(true);
        ventaRepository.save(venta);
    }

    // Consultar ventas por sucursal y fecha
    public List<VentaDto> obtenerVentasPorSucursalYFecha(Long sucursalId, LocalDate fecha) {
        return ventaRepository.findBySucursalAndFecha(sucursalId, fecha).stream()
                .map(this::convertirVentaDto)
                .collect(Collectors.toList());
    }
}


