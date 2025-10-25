package com.universidad.biblioteca.backend_server.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.biblioteca.backend_server.responses.PerfilResponse;
import com.universidad.biblioteca.backend_server.services.PerfilService;

@RestController
@RequestMapping("/me")
public class PerfilController {

    private final PerfilService perfilService;

    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<PerfilResponse> getPerfil(@AuthenticationPrincipal Integer idUsuario) {
        return ResponseEntity.ok(perfilService.obtenerPerfilCompleto(idUsuario));
    }

    // Si quieres exponer permisos por separado (opcional):
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/permisos")
    public ResponseEntity<List<String>> getPermisos(@AuthenticationPrincipal Integer idUsuario) {
        return ResponseEntity.ok(perfilService.obtenerPerfilCompleto(idUsuario).getPermisos());
    }
}
