package com.example.ProyectoFinalGestionDeVentasSupermercado;

import com.example.ProyectoFinalGestionDeVentasSupermercado.dto.ProductoDto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.enums.Categoria;
import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Producto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class ProductoTest {

    private String token;


    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ProductoRepository productoRepository;

    @BeforeEach
    public void setUp() throws Exception {
        token = obtenerTokenAdmin();
    }

   //TOKEN
    private String obtenerTokenAdmin() throws Exception {
        //PONGO MI USUARIO QUE HE ECHO ANTES EN EL /register
        String loginJson = "{ \"username\": \"cintia\", \"password\": \"1234\" }";

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
        String jsonProducto = "{"
                + "\"nombreProducto\": \"Leche entera\","
                + "\"precioProducto\": 1.25,"
                + "\"stockProducto\": 50,"
                + "\"categoriaProducto\": \"ALIMENTACION\""
                + "}";

        mockMvc.perform(
                        post("/api/productos")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonProducto)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreProducto").value("Leche entera"))
                .andExpect(jsonPath("$.precioProducto").value(1.25))
                .andExpect(jsonPath("$.stockProducto").value(50))
                .andExpect(jsonPath("$.categoriaProducto").value("ALIMENTACION"));
    }


    @Test
    void crearProductoInvalidoInvalido() throws Exception {
        String jsonProducto = "{"
                + "\"nombreProducto\": \"\","
                + "\"precioProducto\": -1,"
                + "\"stockProducto\": -5,"
                + "\"categoriaProducto\": null"
                + "}";

        mockMvc.perform(
                        post("/api/productos")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonProducto)
                )
                .andExpect(status().isBadRequest());
    }


    //TEST DE ACTUALIZACION
    @Test
    void actualizarProductoValido() throws Exception {
        Producto productoInicial = productoRepository.save(new Producto("Producto original", 2.50, 30, Categoria.LIMPIEZA));

        String jsonActualizado = "{"
                + "\"nombreProducto\": \"Producto actualizado\","
                + "\"precioProducto\": 3.75,"
                + "\"stockProducto\": 45,"
                + "\"categoriaProducto\": \"PERFUMERIA\""
                + "}";

        mockMvc.perform(
                        put("/api/productos/" + productoInicial.getId())
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonActualizado)
//                                .with(httpBasic("admin", "admin"))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreProducto").value("Producto actualizado"))
                .andExpect(jsonPath("$.precioProducto").value(3.75))
                .andExpect(jsonPath("$.stockProducto").value(45))
                .andExpect(jsonPath("$.categoriaProducto").value("PERFUMERIA"));
    }

    @Test
    void actualizarProductoNegativo() throws Exception {
        String jsonActualizado = "{"
                + "\"nombreProducto\": \"Producto actualizado\","
                + "\"precioProducto\": 3.75,"
                + "\"stockProducto\": 45,"
                + "\"categoriaProducto\": \"BEBIDAS\""
                + "}";

        mockMvc.perform(
                        put("/api/productos/-1")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonActualizado)
//                                .with(httpBasic("admin", "admin"))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void actualizarProductoInexistente() throws Exception {
        String jsonActualizado = "{"
                + "\"nombreProducto\": \"Producto actualizado\","
                + "\"precioProducto\": 3.75,"
                + "\"stockProducto\": 45,"
                + "\"categoriaProducto\": \"BEBIDAS\""
                + "}";

        mockMvc.perform(
                        put("/api/productos/4545451") //4545451 Hemos puesto este número a consciencia sabiendo que no existe en nuestra base de datos, su supera el numero de productos deberia ser cambiado
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonActualizado)
//                                .with(httpBasic("admin", "admin"))
                )
                .andExpect(status().isBadRequest());
    }


    @Test
    void actualizarProductoInvalido() throws Exception {
        Producto productoInicial = productoRepository.save(new Producto("Producto original", 2.50, 30, Categoria.LIMPIEZA));

        String jsonVacio = "{"
                + "\"nombreProducto\": \"\","
                + "\"precioProducto\": -1,"
                + "\"stockProducto\": -5,"
                + "\"categoriaProducto\": null"
                + "}";

        mockMvc.perform(
                        put("/api/productos/" + productoInicial.getId())
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonVacio)
//                                .with(httpBasic("admin", "admin"))
                )
                .andExpect(status().isBadRequest());
    }


    //TEST DE LISTADO

    @Test
    void Listar() throws Exception {
        Producto producto1 = productoRepository.save(new Producto("Pan", 0.99, 100, Categoria.ALIMENTACION));
        Producto producto2 = productoRepository.save(new Producto("Detergente", 3.50, 40, Categoria.LIMPIEZA));
        Producto producto3 = productoRepository.save(new Producto("Perfume", 15.00, 20, Categoria.PERFUMERIA));

        mockMvc.perform(
                        get("/api/productos")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$." + producto1.getId() + ".nombreProducto").value("Pan"))
                .andExpect(jsonPath("$." + producto2.getId() + ".precioProducto").value(3.50))
                .andExpect(jsonPath("$." + producto3.getId() + ".categoriaProducto").value("PERFUMERIA"));

    }



    //TEST DE ELIMINADO

    @Test
    void eliminarProductoValido() throws Exception {
        Producto producto = productoRepository.save(new Producto("Yogur", 1.20, 25, Categoria.ALIMENTACION));

        mockMvc.perform(
                        delete("/api/productos/" + producto.getId())
                                .header("Authorization", "Bearer " + token)
//                                .with(httpBasic("admin", "admin"))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("EL producto ha sido eliminado"));

        // Verificar que el producto está retirado y con stock 0
        Producto retirado = productoRepository.findById(producto.getId()).get();
        assertTrue(retirado.isRetirado());
        assertEquals(0, retirado.getStock());
    }

    @Test
    void eliminarProductoInexistente() throws Exception {
        Long idInexistente = 999999L;//De nuevo es un valor de pruebas, depende de la base de datos

        mockMvc.perform(
                        delete("/api/productos/" + idInexistente)
                                .header("Authorization", "Bearer " + token)
//                                .with(httpBasic("admin", "admin"))
                )
                .andExpect(status().isNotFound());
    }


    @Test
    void eliminarProductoYaRetirado() throws Exception {
        Producto producto = productoRepository.save(new Producto("Zumo", 1.50, 10, Categoria.ALIMENTACION));
        producto.setRetirado(true);
        productoRepository.save(producto);

        mockMvc.perform(
                        delete("/api/productos/" + producto.getId())
                                .header("Authorization", "Bearer " + token)
//                                .with(httpBasic("admin", "admin"))
                )
                .andExpect(status().isNotFound());
    }


}
