package com.example.ProyectoFinalGestionDeVentasSupermercado;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class SucursalTest {

    private String token;


    @Autowired
    private MockMvc mockMvc;









}
