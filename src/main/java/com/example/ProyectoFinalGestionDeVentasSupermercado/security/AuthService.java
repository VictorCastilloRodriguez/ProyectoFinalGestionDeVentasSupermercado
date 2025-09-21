package com.example.ProyectoFinalGestionDeVentasSupermercado.security;

import com.example.ProyectoFinalGestionDeVentasSupermercado.repository.UsuarioRepository;
import com.example.ProyectoFinalGestionDeVentasSupermercado.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    public void registrar(String username, String password){
        usuarioService.registrarUsuario(username,password);
    }

    public String login(String username, String password){
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        return jwtService.generarToken(username);
    }
}
