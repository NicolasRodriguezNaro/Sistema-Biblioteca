package com.universidad.biblioteca.backend_server.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.biblioteca.backend_server.dto.TipoMultaDto;
import com.universidad.biblioteca.backend_server.requests.CrearTipoMultaRequest;
import com.universidad.biblioteca.backend_server.services.TipoMultaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tipos-multa")
public class TipoMultaController {

    private final TipoMultaService service;
    public TipoMultaController(TipoMultaService service) { this.service = service; }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody CrearTipoMultaRequest req) {
        service.crear(req.getNombre(), req.getMonto());
        return ResponseEntity.created(URI.create("/api/tipos-multa"))
                .body(new Mensaje("Tipo de multa creado"));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<TipoMultaDto>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    static class Mensaje { public final String message; public Mensaje(String m){ this.message = m; } }

}
