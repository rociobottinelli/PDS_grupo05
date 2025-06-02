package com.pds.partidosapp.config;

import java.io.IOException;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final SecretKey secretKey;

    public JwtAuthFilter(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = extractJwtFromRequest(request);

            if (token != null && validateToken(token)) {
                String username = extractUsernameFromToken(token);

                if (username != null) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            username, null, null);
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (Exception e) {
            // Manejar excepciones de token inválido aquí
            SecurityContextHolder.clearContext(); // Limpia el contexto de seguridad en caso de error
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage()); // Envia una respuesta de error 401
            return; // Detiene la ejecución
        }

        filterChain.doFilter(request, response);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        // Extraer el token de la cabecera de autorización
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Excluir "Bearer "
        }

        return null;
    }

    private boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);

            // Verifica la firma y la expiración del token
            if (isTokenSignatureValid(claimsJws) && isTokenNotExpired(claimsJws.getBody().getExpiration())) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return false;
    }

    private boolean isTokenSignatureValid(Jws<Claims> claimsJws) {
        // Verifica la firma del token con la clave secreta
        try {
            claimsJws.getBody(); // Esto lanzará una excepción si la firma es inválida
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTokenNotExpired(Date expirationDate) {
        // Verifica si la fecha de expiración del token es posterior a la fecha actual
        return expirationDate != null && !expirationDate.before(new Date());
    }

    public String extractUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();

            // Extraer el nombre de usuario de la carga útil del token
            String username = claims.getSubject();
            return username;
        } catch (Exception e) {
            // Manejar cualquier excepción que pueda ocurrir al analizar el token
            return null;
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().startsWith("/test");
    }

}

