package com.pds.partidosapp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Configurar dominios permitidos para desarrollo
        configuration.setAllowedOriginPatterns(Arrays.asList(
                "http://localhost:*", // Frontend en desarrollo
                "http://127.0.0.1:*", // Alternativa localhost
                "http://192.168.*.*:*" // Red local
        ));

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authz -> authz
                        // Endpoints públicos para registro/login
                        .requestMatchers("/auth/login", "/auth/register").permitAll()

                        // Endpoints de consulta pública (solo GET)
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/deportes/**").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/partidos/**").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/usuarios/**").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/comentarios/**").permitAll()

                        // Testing endpoints
                        .requestMatchers("/test/**").permitAll()

                        // API versioned (si existe) - protegida
                        .requestMatchers("/api/**").authenticated()

                        // Endpoints que modifican datos - requieren autenticación
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/deportes/**").authenticated()
                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/deportes/**").authenticated()
                        .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/deportes/**").authenticated()

                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/partidos/**").authenticated()
                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/partidos/**").authenticated()
                        .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/partidos/**").authenticated()

                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/usuarios/**").authenticated()
                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/usuarios/**").authenticated()
                        .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/usuarios/**").authenticated()

                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/comentarios/**").authenticated()
                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/comentarios/**").authenticated()
                        .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/comentarios/**").authenticated()

                        // Profile endpoints requieren autenticación
                        .requestMatchers("/auth/me").authenticated()

                        // Todo lo demás requiere autenticación
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
