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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.universidad.biblioteca.backend_server.jwt.JwtAuthFilter;
import com.universidad.biblioteca.backend_server.jwt.JwtService;
import com.universidad.biblioteca.backend_server.repositories.PerfilRepository;
import com.universidad.biblioteca.backend_server.repositories.PermisoRepository;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
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
          // 👇 HABILITA CORS para que tome tu CorsConfigurationSource
          .cors(cors -> {}) 
          .csrf(csrf -> csrf.disable())
          .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .authorizeHttpRequests(reg -> reg
              // 👇 Permite preflight
              .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

              // Público
              .requestMatchers(HttpMethod.POST, "/auth/login", "/auth/register", "/auth/refresh", "/auth/verify/finish").permitAll()
              .requestMatchers(HttpMethod.POST, "/auth/verify/start").permitAll()

              // Swagger si lo usas
              .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()

              // Todo lo demás autenticado
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

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Orígenes de tu frontend (Vite)
        config.setAllowedOrigins(java.util.List.of(
            "http://localhost:5173",
            "http://127.0.0.1:5173"
        ));

        config.setAllowedMethods(java.util.List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));

        // Amplía headers por si el navegador agrega otros en preflight
        config.setAllowedHeaders(java.util.List.of(
            "Authorization", "Content-Type", "X-Requested-With",
            "Accept", "Origin"
        ));

        // Headers que expones (opcional)
        config.setExposedHeaders(java.util.List.of("Authorization","Content-Type"));

        // Si NO usas cookies, puedes dejarlo en false (más simple)
        config.setAllowCredentials(false);

        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}