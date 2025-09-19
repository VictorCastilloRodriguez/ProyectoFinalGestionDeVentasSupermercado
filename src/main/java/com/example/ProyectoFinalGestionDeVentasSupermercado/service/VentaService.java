package com.example.ProyectoFinalGestionDeVentasSupermercado.service;

import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.*;
import com.example.ProyectoFinalGestionDeVentasSupermercado.exception.ProductoNotFoundException;
import com.example.ProyectoFinalGestionDeVentasSupermercado.exception.SucursalNotFoundException;
import com.example.ProyectoFinalGestionDeVentasSupermercado.model.*;
import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public VentaDto crearVenta(VentaCreacionDto dto) {
        Sucursal sucursal = sucursalRepository.findById(dto.getIdSucursal())
                .orElseThrow(() -> new SucursalNotFoundException(dto.getIdSucursal()));

        Venta venta = new Venta();
        venta.setSucursal(sucursal);
        venta.setFechaVenta(LocalDateTime.now()); // fecha automática
        venta.setEliminado(false);

        List<DetalleVenta> detalles = dto.getDetalleVentaDtos().stream()
                .map(detDto -> {
                    Producto producto = productoRepository.findById(detDto.getProductoId())
                            .orElseThrow(() -> new ProductoNotFoundException(detDto.getProductoId()));

                    DetalleVenta detalle = new DetalleVenta();
                    detalle.setProducto(producto);
                    detalle.setCantidad(detDto.getCantidad());
                    detalle.setImporte(producto.getPrecio() * detDto.getCantidad()); // cálculo automático
                    detalle.setVenta(venta);
                    return detalle;
                })
                .collect(Collectors.toList()); // compatible con Java 8+

        venta.setDetallesVentas(detalles);
        Venta guardada = ventaRepository.save(venta);

        return convertirVentaDto(guardada);
    }

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
}

