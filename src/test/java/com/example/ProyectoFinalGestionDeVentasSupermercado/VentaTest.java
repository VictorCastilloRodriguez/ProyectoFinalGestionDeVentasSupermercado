package com.example.ProyectoFinalGestionDeVentasSupermercado;

import com.example.ProyectoFinalGestionDeVentasSupermercado.enums.Categoria;
import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Producto;
import com.example.ProyectoFinalGestionDeVentasSupermercado.model.Sucursal;
import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.ProductoRepository;
import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.SucursalRepository;
import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.VentaRepository;
import com.example.ProyectoFinalGestionDeVentasSupermercado.service.VentaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class VentaTest {
    private String token;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private VentaService ventaService;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private SucursalRepository sucursalRepository;
    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() throws Exception {
        token = obtenerTokenAdmin();
    }

    //TOKEN
    private String obtenerTokenAdmin() throws Exception {
        //REGISTRAMOS USUARIO PRUEBAS E INICIAMOS SESION
        String loginJson = "{ \"username\": \"pruebas\", \"password\": \"pruebas\" }";
        mockMvc.perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON).content(loginJson));
        String respuesta = mockMvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content(loginJson)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        return respuesta;
    }

    //TEST DE CREACION
    @Test
    void crearVentaValida() throws Exception { // Crear sucursal y productos necesarios
        Sucursal sucursal = sucursalRepository.save(new Sucursal("Sucursal Central", "Calle Mayor 1"));
        Producto producto1 = productoRepository.save(new Producto("Arroz", 1.50, 20, Categoria.ALIMENTACION));
        Producto producto2 = productoRepository.save(new Producto("Agua", 0.80, 10, Categoria.ALIMENTACION));

        String jsonVenta = "{"
                + "\"sucursalId\": " + sucursal.getId() + ","
                + "\"detalles\": ["
                + "{ \"productoId\": " + producto1.getId() + ", \"cantidad\": 2 },"
                + "{ \"productoId\": " + producto2.getId() + ", \"cantidad\": 1 }"
                + "]"
                + "}";



        mockMvc.perform(post("/api/ventas").header("Authorization", "Bearer " + token).
                        contentType(MediaType.APPLICATION_JSON).content(jsonVenta))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sucursalId").value(sucursal.getId()))
                .andExpect(jsonPath("$.detalles.length()").value(2))
                .andExpect(jsonPath("$.importeTotal").value(3.80)); // 2*1.50 + 1*0.80 } }
    }

    @Test
    void crearVentaConProductoInexistente() throws Exception {
        Sucursal sucursal = sucursalRepository.save(new Sucursal("Sucursal Norte", "Calle Falsa 123"));

        String jsonVenta = "{"
                + "\"sucursalId\": " + sucursal.getId() + ","
                + "\"detalles\": ["
                + "{ \"productoId\": 999999, \"cantidad\": 2 }"
                + "]"
                + "}";

        mockMvc.perform(post("/api/ventas")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonVenta))
                .andExpect(status().isBadRequest());
    }

    @Test
    void crearVentaConSucursalInexistente() throws Exception {
        Producto producto = productoRepository.save(new Producto("Pan", 0.90, 10, Categoria.ALIMENTACION));

        String jsonVenta = "{"
                + "\"sucursalId\": 999999,"
                + "\"detalles\": ["
                + "{ \"productoId\": " + producto.getId() + ", \"cantidad\": 1 }"
                + "]"
                + "}";

        mockMvc.perform(post("/api/ventas")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonVenta))
                .andExpect(status().isBadRequest());
    }

    @Test
    void crearVentaConStockInsuficiente() throws Exception {
        Sucursal sucursal = sucursalRepository.save(new Sucursal("Sucursal Sur", "Avenida Siempre Viva"));
        Producto producto = productoRepository.save(new Producto("Leche", 1.00, 1, Categoria.ALIMENTACION));

        String jsonVenta = "{"
                + "\"sucursalId\": " + sucursal.getId() + ","
                + "\"detalles\": ["
                + "{ \"productoId\": " + producto.getId() + ", \"cantidad\": 5 }"
                + "]"
                + "}";

        mockMvc.perform(post("/api/ventas")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonVenta))
                .andExpect(status().isBadRequest());
    }

    @Test
    void crearVentaConDetalleInvalido() throws Exception {
        Sucursal sucursal = sucursalRepository.save(new Sucursal("Sucursal Este", "Plaza del Sol"));
        Producto producto = productoRepository.save(new Producto("Papel higienico", 2.00, 15, Categoria.LIMPIEZA));

        String jsonVenta = "{"
                + "\"sucursalId\": " + sucursal.getId() + ","
                + "\"detalles\": ["
                + "{ \"productoId\": " + producto.getId() + " }"
                + "]"
                + "}";

        mockMvc.perform(post("/api/ventas")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonVenta))
                .andExpect(status().isBadRequest());
    }
}