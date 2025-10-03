package com.example.ProyectoFinalGestionDeVentasSupermercado.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
public class VentaCreacionDto {

/*    @JsonProperty("fechaVenta")
    @JsonIgnore
    private LocalDate fechaVenta;

    @JsonProperty("sucursalId")
    @NotNull(message = "sucursalId es requerido")
    private Long sucursalId;

    *//*@JsonProperty("clienteId")
    private Long clienteId;*//*

    @JsonProperty("detalles")
    @NotEmpty(message = "detalles es requerido y no puede estar vacío")
    private List<Map<String, Object>> detalles;*/


    @NotNull(message = "sucursalId es requerido")
    private Long sucursalId;

    @NotEmpty(message = "detalles es requerido y no puede estar vacío")
    private List<Map<String, Object>> detalles;
}


