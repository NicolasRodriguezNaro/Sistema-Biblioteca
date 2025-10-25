package com.universidad.biblioteca.backend_server.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.universidad.biblioteca.backend_server.repositories.PerfilRepository;
import com.universidad.biblioteca.backend_server.repositories.PermisoRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwt;
    private final PerfilRepository perfiles;
    private final PermisoRepository permisos;

    public JwtAuthFilter(JwtService jwt, PerfilRepository perfiles, PermisoRepository permisos) {
        this.jwt = jwt; this.perfiles = perfiles; this.permisos = permisos;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, jakarta.servlet.ServletException {

        String header = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(req, res); return;
        }

        String token = header.substring(7);
        try {
            Integer idUsuario = jwt.getUserId(token);
            Integer tvToken = jwt.getTokenVersion(token);

            var perfilOpt = perfiles.findPerfilById(idUsuario);
            if (perfilOpt.isEmpty()) { chain.doFilter(req, res); return; }

            var perfil = perfilOpt.get();

            // Revocación por token_version
            if (!Objects.equals(tvToken, perfil.tokenVersion())) { chain.doFilter(req, res); return; }

            // Estado debe ser ACTIVO (ajusta si quieres permitir más estados)
            if (!"activo".equalsIgnoreCase(perfil.estadoUsuario())) { chain.doFilter(req, res); return; }

            // Construir authorities: ROLE_ + permisos
            List<GrantedAuthority> auths = new ArrayList<>();
            auths.add(new SimpleGrantedAuthority("ROLE_" + perfil.nombreRol().toUpperCase())); // rol
            permisos.permisosPorUsuario(idUsuario)
                    .forEach(p -> auths.add(new SimpleGrantedAuthority("perm:" + p)));

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(idUsuario, null, auths);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            // Si el token es inválido/expirado, seguimos sin auth y dejará 401 por el entry point
        }

        chain.doFilter(req, res);
    }
}
