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

        sucursal.setNombreSucursal(sucursalDto.getNombreSucursal());
        sucursal.setDireccion(sucursalDto.getDireccion());
        Sucursal actualizada = sucursalRepository.save(sucursal);
        return entityToDto(actualizada);
    }

    public void eliminarSucursal(Long id) {
        if (!sucursalRepository.existsById(id)) {
            throw new SucursalNotFoundException(id);
        }
        sucursalRepository.deleteById(id);
    }

    public Map<Long, SucursalDto> listarSucursales() {
        return sucursalRepository.findAll().stream()
                .collect(Collectors.toMap(Sucursal::getId, this::entityToDto));
    }

    private Sucursal dtoToEntity(SucursalDto dto) {
        Sucursal sucursal = new Sucursal();
        sucursal.setNombreSucursal(dto.getNombreSucursal());
        sucursal.setDireccion(dto.getDireccion());
        return sucursal;
    }

    private SucursalDto entityToDto(Sucursal sucursal) {
        return new SucursalDto(sucursal.getNombreSucursal(), sucursal.getDireccion());
    }
}


