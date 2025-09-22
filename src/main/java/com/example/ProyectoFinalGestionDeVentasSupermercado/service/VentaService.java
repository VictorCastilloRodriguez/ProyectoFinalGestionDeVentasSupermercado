package com.example.ProyectoFinalGestionDeVentasSupermercado.service;

import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.VentaCreacionDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.VentaDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.model.LineaVenta;
import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Producto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Venta;
import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.ProductoRepository;
import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.VentaRepository;
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
    private ModelMapper mapper;

 
    @Transactional
    public VentaDto crearVenta(VentaCreacionDto ventaDto) {
        Venta venta = new Venta();

        // Mapea campos imprescindibles
        venta.setFecha(ventaDto.getFecha() != null ? ventaDto.getFecha() : LocalDate.now());
        venta.setClienteId(ventaDto.getClienteId());
        venta.setSucursalId(ventaDto.getSucursalId());

        // Mapear líneas DTO
        if (ventaDto.getLineas() != null) {
            ventaDto.getLineas().forEach(lineaDto -> {
                LineaVenta linea = new LineaVenta();
                linea.setProductoId(lineaDto.getProductoId());
                linea.setNombreProducto(lineaDto.getNombre()); 
                linea.setPrecio(lineaDto.getPrecio());
                linea.setCantidad(lineaDto.getCantidad() != null ? lineaDto.getCantidad() : 0);
                linea.setVenta(venta);
                venta.addLinea(linea);
            });
        }

        // Valida stock EN TODAS las líneas antes de guardar la venta
        validarStockPreventa(venta);

        ventaRepository.save(venta);

        // Por cada línea: decrementar el stock, crear/actualizar producto/vecesVendido
        if (venta.getLineas() != null) {
            venta.getLineas().forEach(this::procesarLineaParaStockYProducto);
        }

        return mapper.map(venta, VentaDto.class);
    }

    /*
     Valida que exista stock suficiente para todas las líneas de la venta.
     Lanza ResponseStatusException(HttpStatus.BAD_REQUEST) si falta stock o producto no existe.
     */
    private void validarStockPreventa(Venta venta) {
        if (venta.getLineas() == null) return;

        for (LineaVenta linea : venta.getLineas()) {
            int cantidad = linea.getCantidad() != null ? linea.getCantidad() : 0;
            Long productoId = linea.getProductoId();

            if (productoId == null) {
                // No se permite vender productos sin identificar
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "No se puede vender un producto sin identificar: " + linea.getNombreProducto());
            }

            Producto producto = productoRepository.findById(productoId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Producto no encontrado: " + productoId));

            int stockActual = producto.getStock() != null ? producto.getStock() : 0;
            if (stockActual < cantidad) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Stock insuficiente para el producto " + producto.getNombreProducto()
                                + ": disponible=" + stockActual + ", solicitado=" + cantidad);
            }
        }
    }

    private void procesarLineaParaStockYProducto(LineaVenta linea) {
        Long productoId = linea.getProductoId();
        int cantidad = linea.getCantidad() != null ? linea.getCantidad() : 0;

        Optional<Producto> opt = productoRepository.findById(productoId);
        if (!opt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Producto desapareció antes de procesar: " + productoId);
        }

        Producto producto = opt.get();

        // Actualizar nombreProducto si la línea aporta nombre y es distinto
        String nombreDesdeLinea = linea.getNombreProducto();
        if (nombreDesdeLinea != null && !nombreDesdeLinea.equals(producto.getNombreProducto())) {
            producto.setNombreProducto(nombreDesdeLinea);
        }

        // Actualizar precio si es distinto
        Double precioDesdeLinea = linea.getPrecio();
        if (precioDesdeLinea != null && !precioDesdeLinea.equals(producto.getPrecio())) {
            producto.setPrecio(precioDesdeLinea);
        }

        // Decrementar stock asegurando que la cantidad de stock es superior a la vendida
        int stockPrevio = producto.getStock() != null ? producto.getStock() : 0;
        producto.setStock(Math.max(stockPrevio - cantidad, 0));

        // Incrementar contador de veces vendido.
        Integer veces = producto.getVecesVendido() != null ? producto.getVecesVendido() : 0;
        producto.setVecesVendido(veces + cantidad);

        productoRepository.save(producto);
    }

    
    // Obtener ventas por sucursal y fecha.
     
    @Transactional(readOnly = true)
    public List<VentaDto> obtenerVentasPorSucursalYFecha(Long sucursalId, LocalDate fecha) {
        List<Venta> ventas = ventaRepository.findBySucursalIdAndFechaAndEliminadoFalse(sucursalId, fecha);
        return ventas.stream()
                .map(v -> mapper.map(v, VentaDto.class))
                .collect(Collectors.toList());
    }

    
    // Anular venta: borrado lógico usando campo eliminado si existe.
   
    @Transactional
    public void anularVenta(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Venta no encontrada"));
        if (venta.getEliminado() != null) {
            venta.setEliminado(true);
        } else {
            venta.setAnulada(true);
        }
        ventaRepository.save(venta);
    }
}
