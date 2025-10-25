package com.universidad.biblioteca.backend_server.seguridad;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.*;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.universidad.biblioteca.backend_server.jwt.JwtAuthFilter;
import com.universidad.biblioteca.backend_server.jwt.JwtService;
import com.universidad.biblioteca.backend_server.repositories.PerfilRepository;
import com.universidad.biblioteca.backend_server.repositories.PermisoRepository;

@Configuration
@EnableMethodSecurity(prePostEnabled = true) // habilita @PreAuthorize
public class SecurityConfig {

    @Bean
    public JwtService jwtService(@Value("${security.jwt.secret}") String secret) {
        return new JwtService(secret);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           JwtService jwtService,
                                           PerfilRepository perfilRepo,
                                           PermisoRepository permisoRepo) throws Exception {

        JwtAuthFilter jwtFilter = new JwtAuthFilter(jwtService, perfilRepo, permisoRepo);

        http
          .csrf(csrf -> csrf.disable())
          .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .authorizeHttpRequests(reg -> reg
              // Público
              .requestMatchers(HttpMethod.POST, "/auth/login", "/auth/register", "/auth/refresh", "/auth/verify/finish").permitAll()
              .requestMatchers(HttpMethod.POST, "/auth/verify/start").permitAll()
              // Swagger si lo usas
              .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
              // Todo lo demás, autenticado
              .anyRequest().authenticated()
          )
          .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
          .exceptionHandling(eh -> eh
              .authenticationEntryPoint((req, res, ex) -> {
                  res.setStatus(401);
                  res.setContentType("application/json");
                  res.getWriter().write("{\"error\":\"No autorizado\"}");
              })
          );

        return http.build();
    }
}