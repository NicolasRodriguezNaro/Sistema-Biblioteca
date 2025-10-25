package com.universidad.biblioteca.backend_server.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.biblioteca.backend_server.dto.PerfilDto;
import com.universidad.biblioteca.backend_server.services.PerfilDetalleService;

@RestController
@RequestMapping("/me")
public class PerfilFullController {
    
    private final PerfilDetalleService service;

    public PerfilFullController(PerfilDetalleService service) {
        this.service = service;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/full")
    public ResponseEntity<PerfilDto> getPerfilFull(@AuthenticationPrincipal Integer idUsuario) {
        return ResponseEntity.ok(service.miPerfilCompleto(idUsuario));
    }
}
