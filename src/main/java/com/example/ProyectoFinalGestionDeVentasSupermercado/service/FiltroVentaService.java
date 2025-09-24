package com.example.ProyectoFinalGestionDeVentasSupermercado.service;

import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Venta;
import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.DetalleVentaRepository;
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
    private DetalleVentaRepository detalleVentaRepository;

    public List<Venta> filtroVentasSucursalFecha(Long idSucursal, LocalDate fecha) {
        return ventaRepository.findAll().stream()
                .filter(v -> idSucursal == null || v.getSucursal().equals(idSucursal))
                .filter(v -> fecha == null || v.getFechaVenta().equals(fecha))
                .collect(Collectors.toList());
    }

    public List<Object[]> listarProductosMasVendidos() {
        return new ArrayList<>(detalleVentaRepository.findProductosMasVendidos());
    }

    public Double obtenerTotalIngresos() {
        return detalleVentaRepository.totalIngresosVenta();
    }

    public List<Object[]> obtenerTotalIngresosPorVenta() {
        return obtenerTotalIngresosPorVenta();
    }

    public List<Object[]> obtenerTotalIngresosPorSucursal() {
        return obtenerTotalIngresosPorSucursal();
    }
}

