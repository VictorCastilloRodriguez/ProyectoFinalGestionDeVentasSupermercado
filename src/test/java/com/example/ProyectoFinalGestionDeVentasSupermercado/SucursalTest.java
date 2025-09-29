package com.example.ProyectoFinalGestionDeVentasSupermercado;


import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.ProductoDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.SucursalDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.enums.Categoria;
import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Producto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Sucursal;
import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.SucursalRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class SucursalTest {


    private String token;


    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private SucursalRepository sucursalRepository;

    @BeforeEach
    public void setUp() throws Exception {
        token = obtenerTokenAdmin();
    }

    //TOKEN
    private String obtenerTokenAdmin() throws Exception {
        //REGISTRAMOS USUARIO PRUEBAS E INICIAMOS SESION
        String loginJson = "{ \"username\": \"pruebas\", \"password\": \"pruebas\" }";

        mockMvc.perform( post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson));

        String respuesta = mockMvc.perform(
                        post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(loginJson)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return respuesta;
    }

    //TEST DE CREACION

    @Test
    void crearProductoValido() throws Exception {
        String jsonSucursal = "{"
                + "\"nombreSucursal\": \"Sucursal prueba\","
                + "\"direccionSucursal\": \"direccion prueba\""
                + "}";

        mockMvc.perform(
                        post("/api/sucursales")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonSucursal)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreSucursal").value("Sucursal prueba"))
                .andExpect(jsonPath("$.direccionSucursal").value("direccion prueba"));
    }


    @Test
    void crearProductoInvalidoInvalido() throws Exception {
        String jsonSucursal = "{"
                + "\"nombreSucursal\": \"\","
                + "\"direccionSucursal\": null"
                + "}";

        mockMvc.perform(
                        post("/api/productos")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonSucursal)
                )
                .andExpect(status().isBadRequest());
    }

    //TEST DE ACTUALIZACION
    @Test
    void actualizarProductoValido() throws Exception {
        Sucursal sucursalInicial = sucursalRepository.save(new Sucursal("sucursal 1","Izquierda"));

        String jsonSucursal = "{"
                + "\"nombreSucursal\": \"Sucursal actualizada\","
                + "\"direccionSucursal\": \"direccion actualizada\""
                + "}";

        mockMvc.perform(
                        put("/api/sucursales/" + sucursalInicial.getId())
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonSucursal)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreSucursal").value("Sucursal actualizada"))
                .andExpect(jsonPath("$.direccionSucursal").value("direccion actualizada"));
    }


    @Test
    void actualizarProductoNegativo() throws Exception {
        String jsonSucursal = "{"
                + "\"nombreSucursal\": \"Sucursal actualizada\","
                + "\"direccionSucursal\": \"direccion actualizada\""
                + "}";

        mockMvc.perform(
                        put("/api/sucursales/-1")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonSucursal)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void actualizarProductoInexistente() throws Exception {
        String jsonSucursal = "{"
                + "\"nombreSucursal\": \"Sucursal actualizada\","
                + "\"direccionSucursal\": \"direccion actualizada\""
                + "}";

        mockMvc.perform(
                        put("/api/sucursales/99999999") //99999999 usamos este numero como ejemplo para probar porque sabemos que no está en nuestra base de datos
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonSucursal)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void actualizarProductoInvalido() throws Exception {
        Sucursal sucursalInicial = sucursalRepository.save(new Sucursal("sucursal 1","Izquierda"));

        String jsonSucursal = "{"
                + "\"nombreSucursal\": \"\","
                + "\"direccionSucursal\": \"direccion actualizada\""
                + "}";

        mockMvc.perform(
                        put("/api/sucursales/" + sucursalInicial.getId())
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonSucursal)
                )
                .andExpect(status().isBadRequest());




    }


    //TEST DE LISTADO

    @Test
    void Listar() throws Exception {

        Sucursal sucursal1 = sucursalRepository.save(new Sucursal("sucursal 1","Izquierda"));
        Sucursal sucursal2 = sucursalRepository.save(new Sucursal("sucursal 2","Derecha"));
        Sucursal sucursal3 = sucursalRepository.save(new Sucursal("sucursal 3","Centro"));


        mockMvc.perform(
                        get("/api/sucursales")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$." + sucursal1.getId() + ".nombreSucursal").value("sucursal 1"))
                .andExpect(jsonPath("$." + sucursal2.getId() + ".nombreSucursal" ).value("sucursal 2"))
                .andExpect(jsonPath("$." + sucursal3.getId() + ".direccionSucursal").value("Centro"));

    }

    //TEST DE ELIMINADO

    @Test
    void eliminarProductoValido() throws Exception {
        Sucursal sucursalInicial = sucursalRepository.save(new Sucursal("sucursal 1","Izquierda"));


        mockMvc.perform(
                        delete("/api/sucursales/" + sucursalInicial.getId())
                                .header("Authorization", "Bearer " + token)
//                                .with(httpBasic("admin", "admin"))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("La sucursal ha sido eliminada"));


        Sucursal sucursalCerrada = sucursalRepository.findById(sucursalInicial.getId()).get();
        assertTrue(sucursalCerrada.isCerrada());
    }


    @Test
    void eliminarProductoInexistente() throws Exception {
        Long idInexistente = 999999L;//De nuevo es un valor de pruebas, depende de la base de datos

        mockMvc.perform(
                        delete("/api/sucursales/" + idInexistente)
                                .header("Authorization", "Bearer " + token)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminarProductoYaRetirado() throws Exception {
        Sucursal sucursalInicial = sucursalRepository.save(new Sucursal("sucursal 1","Izquierda"));
        sucursalInicial.setCerrada(true);
        sucursalRepository.save(sucursalInicial);

        mockMvc.perform(
                        delete("/api/productos/" + sucursalInicial.getId())
                                .header("Authorization", "Bearer " + token)
//                                .with(httpBasic("admin", "admin"))
                )
                .andExpect(status().isNotFound());
    }




}
