package com.example.ProyectoFinalGestionDeVentasSupermercado.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    String key = "Pajarito123Cangrejo456Ardilla789";
        SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes());
        public String generarToken(String username){
            return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis()+1000*60*60*2))
                    .signWith(secretKey, Jwts.SIG.HS256)
                    .compact();
        }

        public String validarToken(String token){
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        }
}
