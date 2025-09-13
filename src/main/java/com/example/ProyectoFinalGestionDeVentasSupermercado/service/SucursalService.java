package com.example.ProyectoFinalGestionDeVentasSupermercado.service;

import com.example.ProyectoFinalGestionDeVentasSupermercado.exception.SucursalNotFoundException;
import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Sucursal;
import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SucursalService {
    @Autowired
    private SucursalRepository sucursalRepository;

    // Crear sucursal
    public Sucursal crearSucursal(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }

    // Actualizar sucursal
    public Sucursal actualizarSucursal(Long id, Sucursal sucursalActualizada) {
        Sucursal sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> new SucursalNotFoundException("Sucursal con id: " + id + "no encontrada"));

        sucursal.setNombreSucursal(sucursalActualizada.getNombreSucursal());
        sucursal.setDireccion(sucursalActualizada.getDireccion());
        return sucursalRepository.save(sucursal);
    }

    // Listar sucursal
    public Map<Long, Sucursal> listarSucursales() {
        return sucursalRepository.findAll().stream()
                .collect(Collectors.toMap(Sucursal::getId, sucursal -> sucursal));
    }

    // Eliminar sucursal
    public void eliminarSucursal(Long id) {
        if (!sucursalRepository.existsById(id)) {
            throw new SucursalNotFoundException("Sucursal con id: " + id + " no encontrada");
        }
        sucursalRepository.deleteById(id);
    }
}

