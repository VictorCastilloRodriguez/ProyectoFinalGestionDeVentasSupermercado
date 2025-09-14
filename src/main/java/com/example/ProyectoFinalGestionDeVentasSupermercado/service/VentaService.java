package com.example.ProyectoFinalGestionDeVentasSupermercado.service;

import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.DetalleVentaDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.VentaDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.model.DetalleVenta;
import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Venta;
import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.DetalleVentaRepository;
import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDate;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    //Convertir la venta en ventaDto
    public VentaDto convertirVentaDto(Venta venta){
        VentaDto ventaDto = new VentaDto();

        ventaDto.setIdVenta(venta.getId());
        ventaDto.setIdSucursal(venta.getSucursal().getId());
        ventaDto.setFecha(venta.getFechaVenta().toLocalDate());

        List<DetalleVentaDto> detallesDto = venta.getDetallesVentas().stream().map(det ->{
            DetalleVentaDto detalleVentaDto = new DetalleVentaDto();
            detalleVentaDto.setProductoId(det.getProducto().getId());
            detalleVentaDto.setCantidad(det.getCantidad());
            detalleVentaDto.setImporte(det.getCantidad()*det.getProducto().getPrecio());
            return detalleVentaDto;
        }).toList();
        ventaDto.setDetalleVentaDtos(detallesDto);
        return ventaDto;
    }

    public Venta crearVenta(Venta venta){
        return ventaRepository.save(venta);
    }
}
