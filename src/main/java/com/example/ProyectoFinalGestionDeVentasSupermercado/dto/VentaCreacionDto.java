package com.example.ProyectoFinalGestionDeVentasSupermercado.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
public class VentaCreacionDto {

    @JsonProperty("fechaVenta")
    private LocalDate fechaVenta;

    @JsonProperty("sucursalId")
    @NotNull(message = "sucursalId es requerido")
    private Long sucursalId;

    @JsonProperty("clienteId")
    private Long clienteId;

    @JsonProperty("detalles")
    @NotEmpty(message = "lineas es requerido y no puede estar vacío")
    private List<Map<String, Object>> detalles;
}
