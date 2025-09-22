package com.example.ProyectoFinalGestionDeVentasSupermercado.service;

import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.SucursalDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.exception.SucursalNotFoundException;
import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Sucursal;
import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.SucursalRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    public SucursalDto crearSucursal(SucursalDto dto) {
        Sucursal sucursal = dtoToEntity(dto);
        return entityToDto(sucursalRepository.save(sucursal));
    }

    public SucursalDto actualizarSucursal(Long id, @Valid SucursalDto sucursalDto) {
        Sucursal sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> new SucursalNotFoundException("Sucursal con id: " + id + " no encontrada"));

        sucursal.setNombre(sucursalDto.getNombreSucursal());
        sucursal.setDireccion(sucursalDto.getDireccionSucursal());
        return entityToDto(sucursalRepository.save(sucursal));
    }

    public void eliminarSucursal(Long id) {
        Sucursal sucursalEliminada = sucursalRepository.findById(id)
                .orElseThrow(() -> new SucursalNotFoundException("Sucursal con id: " + id + " no encontrada"));

        if (sucursalEliminada.isCerrada()) {
            throw new SucursalNotFoundException("Sucursal con id: " + id + " ya está cerrada");
        }
        sucursalEliminada.setCerrada(true);
        sucursalRepository.save(sucursalEliminada);
    }

    public Map<Long, SucursalDto> listarSucursales() {
        return sucursalRepository.findAll().stream()
                .filter(sucursal -> !sucursal.isCerrada())
                .collect(Collectors.toMap(Sucursal::getId, this::entityToDto));
    }

    private Sucursal dtoToEntity(SucursalDto dto) {
        Sucursal sucursal = new Sucursal();
        sucursal.setNombre(dto.getNombreSucursal());
        sucursal.setDireccion(dto.getDireccionSucursal());
        return sucursal;
    }

    private SucursalDto entityToDto(Sucursal sucursal) {
        return new SucursalDto(sucursal.getNombre(), sucursal.getDireccion());
    }
}