package com.pds.partidosapp.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.pds.partidosapp.model.entity.Usuario;
import com.pds.partidosapp.enums.NivelEnum;

import java.security.Key;
import java.util.*;

@Component
public class JwtTokenProvider {

  @Value("${jwt.secret:partidosSecretKeyWith32CharsMinimum}")
  private String jwtSecret;

  @Value("${jwt.expiration:86400000}")
  private long expirationMs;

  private Key secretKey;

  @PostConstruct
  public void init() {
    this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
  }

  public String generateToken(Usuario usuario) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("nombreUsuario", usuario.getNombreUsuario());
    claims.put("nivel", usuario.getNivelJuego().toString());

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(usuario.getEmail())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
        .signWith(secretKey)
        .compact();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  public String getEmail(String token) {
    return getClaims(token).getSubject();
  }

  public String getNombreUsuario(String token) {
    Claims claims = getClaims(token);
    return (String) claims.get("nombreUsuario");
  }

  public NivelEnum getNivel(String token) {
    Claims claims = getClaims(token);
    return NivelEnum.valueOf((String) claims.get("nivel"));
  }

  private Claims getClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
  }
}