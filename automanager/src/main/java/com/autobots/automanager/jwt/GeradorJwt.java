package com.autobots.automanager.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class GeradorJwt {
    private final String segredo = "trocar-por-segredo-seguro";
    private final long expiracaoMs = 1000L * 60 * 60;

    public String gerar(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiracaoMs))
                .signWith(SignatureAlgorithm.HS256, segredo.getBytes())
                .compact();
    }

    public String getSegredo() {
        return segredo;
    }
}