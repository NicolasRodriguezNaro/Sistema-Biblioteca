package com.universidad.biblioteca.backend_server.controllers;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.biblioteca.backend_server.dto.ReservaDto;
import com.universidad.biblioteca.backend_server.requests.CrearReservaRequest;
import com.universidad.biblioteca.backend_server.services.ReservaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService service;

    public ReservaController(ReservaService service) {
        this.service = service;
    }

    @PreAuthorize("hasAuthority('perm:reserva.crear')")
    @PostMapping
    public ResponseEntity<?> crear(@AuthenticationPrincipal Integer idUsuario,
                                @Valid @RequestBody CrearReservaRequest req) {
        service.crear(idUsuario, req.getIdLibro(), req.getFecha());
        return ResponseEntity.created(URI.create("/api/reservas"))
                .body(new Mensaje("Reserva creada"));
    }

    @PreAuthorize("hasAuthority('perm:reserva.cancelar')")
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelar(@PathVariable Integer id) {
        service.cancelar(id);
        return ResponseEntity.ok(new Mensaje("Reserva cancelada"));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    @GetMapping
    public ResponseEntity<List<ReservaDto>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Integer id) {
        return service.detalle(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(new Mensaje("Reserva no encontrada")));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<ReservaDto>> porUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(service.listarPorUsuario(idUsuario));
    }

    @GetMapping("/usuario/{idUsuario}/activas")
    public ResponseEntity<List<ReservaDto>> activasUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(service.listarActivasPorUsuario(idUsuario));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    @GetMapping("/libro/{idLibro}")
    public ResponseEntity<List<ReservaDto>> porLibro(@PathVariable Integer idLibro) {
        return ResponseEntity.ok(service.listarPorLibro(idLibro));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    @GetMapping("/activas")
    public ResponseEntity<List<ReservaDto>> activas() {
        return ResponseEntity.ok(service.listarActivas());
    }

    // Reservas activas/pendientes por libro (para bibliotecario/admin)
    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    @GetMapping("/libro/{idLibro}/activas")
    public ResponseEntity<List<ReservaDto>> activasPorLibro(@PathVariable Integer idLibro) {
        return ResponseEntity.ok(service.listarActivasPorLibro(idLibro));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    @GetMapping("/libro/{idLibro}/activas/count")
    public ResponseEntity<?> activasPorLibroCount(@PathVariable Integer idLibro) {
        int count = service.contarActivasPorLibro(idLibro);
        return ResponseEntity.ok(Map.of("idLibro", idLibro, "reservasActivas", count));
    }

    static class Mensaje {
        public final String message;

        public Mensaje(String m) {
            this.message = m;
        }
    }
}
