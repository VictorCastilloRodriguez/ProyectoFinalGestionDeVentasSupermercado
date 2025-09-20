package com.example.ProyectoFinalGestionDeVentasSupermercado.security;

import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @

}
