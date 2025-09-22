package com.example.ProyectoFinalGestionDeVentasSupermercado.service;

import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.SucursalDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.VentaDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.model.DetalleVenta;
import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Producto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Sucursal;
import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Venta;
import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.ProductoRepository;
import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.SucursalRepository;
import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.VentaRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private ModelMapper mapper;

    @Transactional
    public VentaDto crearVentaDesdeJson(JsonNode payload) {
        Venta venta = new Venta();
        venta.setFechaVenta(LocalDate.now());

        Long sucursalId = payload.hasNonNull("sucursalId") ? payload.get("sucursalId").asLong() : null;
        if (sucursalId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "sucursalId es obligatorio");
        }

        Sucursal sucursal = sucursalRepository.findById(sucursalId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sucursal no encontrada: " + sucursalId));
        venta.setSucursal(sucursal);

        JsonNode detallesNode = payload.get("detalles");
        if (detallesNode == null || !detallesNode.isArray()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo 'detalles' debe ser un arreglo");
        }

        double totalVenta = 0.0;

        for (JsonNode l : detallesNode) {
            Long productoId = l.hasNonNull("productoId") ? l.get("productoId").asLong() : null;
            Integer cantidad = l.hasNonNull("cantidad") ? l.get("cantidad").asInt() : 0;

            if (productoId == null || cantidad <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cada detalle debe tener productoId y cantidad > 0");
            }

            Producto producto = productoRepository.findById(productoId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Producto no encontrado: " + productoId));

            DetalleVenta detalle = new DetalleVenta();
            detalle.setProductoId(productoId);
            detalle.setNombreProducto(producto.getNombre());
            detalle.setPrecio(producto.getPrecio());
            detalle.setCantidad(cantidad);
            detalle.setVenta(venta);

            venta.getDetallesVentas().add(detalle);
            totalVenta += producto.getPrecio() * cantidad;
        }

        validarStockPreventa(venta);
        venta.setImporteTotal(totalVenta);

        Venta ventaGuardada = ventaRepository.save(venta);
        ventaGuardada.getDetallesVentas().forEach(this::procesarDetalleParaStockYProducto);

        VentaDto dto = mapper.map(ventaGuardada, VentaDto.class);
        dto.setSucursalId(sucursal.getId());
        dto.setSucursal(new SucursalDto(sucursal.getNombre(), sucursal.getDireccion()));
        return dto;
    }

    private void validarStockPreventa(Venta venta) {
        if (venta.getDetallesVentas() == null) return;

        for (DetalleVenta dv : venta.getDetallesVentas()) {
            int cantidad = dv.getCantidad() != null ? dv.getCantidad() : 0;
            Long productoId = dv.getProductoId();

            if (productoId == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "No se puede vender un producto sin identificar: " + dv.getNombreProducto());
            }

            Producto producto = productoRepository.findById(productoId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Producto no encontrado: " + productoId));

            int stockActual = producto.getStock() != null ? producto.getStock() : 0;
            if (stockActual < cantidad) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Stock insuficiente para el producto " + producto.getNombre()
                                + ": disponible=" + stockActual + ", solicitado=" + cantidad);
            }
        }
    }

    private void procesarDetalleParaStockYProducto(DetalleVenta dv) {
        Long productoId = dv.getProductoId();
        int cantidad = dv.getCantidad() != null ? dv.getCantidad() : 0;

        Optional<Producto> opt = productoRepository.findById(productoId);
        if (!opt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Producto desapareció antes de procesar: " + productoId);
        }

        Producto producto = opt.get();

        if (dv.getNombreProducto() != null && !dv.getNombreProducto().equals(producto.getNombre())) {
            producto.setNombre(dv.getNombreProducto());
        }

        if (dv.getPrecio() != null && !dv.getPrecio().equals(producto.getPrecio())) {
            producto.setPrecio(dv.getPrecio());
        }

        int stockPrevio = producto.getStock() != null ? producto.getStock() : 0;
        producto.setStock(Math.max(stockPrevio - cantidad, 0));

        int vecesPrevias = producto.getVecesVendido() != null ? producto.getVecesVendido() : 0;
        producto.setVecesVendido(vecesPrevias + cantidad);

        productoRepository.save(producto);
    }

    @Transactional(readOnly = true)
    public List<VentaDto> obtenerVentasPorSucursalYFecha(Long sucursalId, LocalDate fecha) {
        List<Venta> ventas = ventaRepository.findBySucursalIdAndFechaVentaAndEliminadoFalse(sucursalId, fecha);
        return ventas.stream()
                .map(v -> mapper.map(v, VentaDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void anularVenta(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Venta no encontrada"));
        venta.setEliminado(true);
        ventaRepository.save(venta);
    }
}
