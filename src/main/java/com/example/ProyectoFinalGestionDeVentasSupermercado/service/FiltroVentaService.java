package com.example.ProyectoFinalGestionDeVentasSupermercado.service;

import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.IngresoPorSucursalDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.ProductoMasVendidoDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Producto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Sucursal;
import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Venta;
import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.DetalleVentaRepository;
import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.ProductoRepository;
import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.SucursalRepository;
import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FiltroVentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    public List<Venta> filtroVentasSucursalFecha(Long idSucursal, LocalDate fecha) {
        return ventaRepository.findAll().stream()
                .filter(v -> idSucursal == null || v.getSucursal().equals(idSucursal))
                .filter(v -> fecha == null || v.getFechaVenta().equals(fecha))
                .collect(Collectors.toList());
    }

    public List<ProductoMasVendidoDto> listarProductosMasVendidos() {
        List<Object[]> resultados = detalleVentaRepository.findProductosMasVendidos();
        return resultados.stream().map(obj-> {
                Long productoId = (Long) obj[0];
                Integer cantidad = obj[1] != null ? ((Number) obj[1]).intValue() : 0;

                String nombre = productoRepository.findById(productoId)
                        .map(Producto::getNombre)
                        .orElse("Producto desconocido.");
                return new ProductoMasVendidoDto(productoId, nombre, cantidad);
        }).collect(Collectors.toList());
    }

    public Double obtenerTotalIngresos() {
        return detalleVentaRepository.totalIngresosVenta();
    }

    public List<Object[]> obtenerTotalIngresosPorVenta() {
        return detalleVentaRepository.totalIngresosPorVenta();
    }

    public List<IngresoPorSucursalDto> obtenerTotalIngresosPorSucursal() {
        List<Object[]> resultados = detalleVentaRepository.totalIngresosPorSucursal();
        return resultados.stream().map(obj -> {
            Long sucursalId = (Long) obj[0];
            Double total = (Double) obj[1];

            String nombreSusursal= sucursalRepository.findById(sucursalId)
                    .map(Sucursal::getNombre)
                    .orElse("Sucursal desconocida.");
            return new IngresoPorSucursalDto(sucursalId, nombreSusursal, total);
        }).collect(Collectors.toList());
    }
}

