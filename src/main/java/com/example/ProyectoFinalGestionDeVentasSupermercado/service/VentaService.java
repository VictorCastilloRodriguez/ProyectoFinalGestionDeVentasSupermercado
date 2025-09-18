package com.example.ProyectoFinalGestionDeVentasSupermercado.service;

import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.DetalleVentaDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.VentaDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.exception.ProductoNotFoundException;
import com.example.ProyectoFinalGestionDeVentasSupermercado.exception.SucursalNotFoundException;
import com.example.ProyectoFinalGestionDeVentasSupermercado.model.DetalleVenta;
import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Producto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Sucursal;
import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Venta;
import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.DetalleVentaRepository;
import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.ProductoRepository;
import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.SucursalRepository;
import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VentaService {
    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private ProductoRepository productoRepository;

    /* public VentaDto crearVenta(Venta dto) {
         Sucursal sucursal = sucursalRepository.findById(dto.getIdSucursal())
                 .orElseThrow(() -> new SucursalNotFoundException(dto.getIdSucursal()));

         Venta venta = new Venta();
         venta.setSucursal(sucursal);
         venta.setFechaVenta(dto.getFecha().atStartOfDay());
         venta.setEliminado(false);

         List<DetalleVenta> detalles = dto.getDetalleVentaDtos().stream().map(detDto -> {
             Producto producto = productoRepository.findById(detDto.getProductoId())
                     .orElseThrow(() -> new ProductoNotFoundException(detDto.getProductoId()));
             DetalleVenta detalle = new DetalleVenta();
             detalle.setProducto(producto);
             detalle.setCantidad(detDto.getCantidad());
             detalle.setVenta(venta);
             return detalle;
         }).collect(Collectors.toList());

         venta.setDetallesVentas(detalles);
         Venta guardada = ventaRepository.save(venta);
         return convertirVentaDto(guardada);
     }*/

    public Venta crearVenta(Venta venta) {
        Sucursal sucursal = sucursalRepository.findById(venta.getSucursal().getId())
                .orElseThrow(() -> new SucursalNotFoundException("Sucursal no encontrada"));

        venta.setSucursal(sucursal);
        venta.setFechaVenta(LocalDateTime.now());

        for (DetalleVenta detalle : venta.getDetallesVentas()) {
            Producto producto = productoRepository.findById(detalle.getProducto().getId())
                    .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));

            detalle.setProducto(producto);
            detalle.setVenta(venta);
        }

        return ventaRepository.save(venta);
    }





    public VentaDto convertirVentaDto(Venta venta) {
        VentaDto dto = new VentaDto();
        dto.setIdVenta(venta.getId());
        dto.setIdSucursal(venta.getSucursal().getId());
        dto.setFecha(venta.getFechaVenta().toLocalDate());

        List<DetalleVentaDto> detallesDto = venta.getDetallesVentas().stream().map(det -> {
            DetalleVentaDto detalleDto = new DetalleVentaDto();
            detalleDto.setProductoId(det.getProducto().getId());
            detalleDto.setCantidad(det.getCantidad());
            detalleDto.setImporte(det.getCantidad() * det.getProducto().getPrecio());
            return detalleDto;
        }).collect(Collectors.toList());

        dto.setDetalleVentaDtos(detallesDto);
        return dto;
    }
}

