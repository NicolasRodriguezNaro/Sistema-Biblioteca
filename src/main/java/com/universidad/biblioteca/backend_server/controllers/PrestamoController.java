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

import com.universidad.biblioteca.backend_server.dto.PrestamoDto;
import com.universidad.biblioteca.backend_server.requests.ActualizarEstadoPrestamoRequest;
import com.universidad.biblioteca.backend_server.requests.CrearPrestamoRequest;
import com.universidad.biblioteca.backend_server.services.PrestamoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {
    
    private final PrestamoService service;
    public PrestamoController(PrestamoService service) { this.service = service; }

    @PreAuthorize("hasAuthority('perm:prestamo.crear')")
    @PostMapping
    public ResponseEntity<?> crear(@AuthenticationPrincipal Integer idUsuario,
                                    @Valid @RequestBody CrearPrestamoRequest req) {
        service.crear(
            idUsuario, req.getIdLibro(), req.getNumeroEjemplar(),
            req.getFechaPrestamo(), req.getFechaDevolucionProgramada(), req.getIdReserva()
        );
        return ResponseEntity.created(URI.create("/api/prestamos"))
                .body(new Mensaje("Préstamo creado"));
    }

    @PreAuthorize("hasAuthority('perm:prestamo.actualizar_estado')")
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Integer id,
                                              @Valid @RequestBody ActualizarEstadoPrestamoRequest req) {
        service.actualizarEstado(id, req.getEstado(), req.getFechaDevolucionReal());
        return ResponseEntity.ok(new Mensaje("Estado actualizado a " + req.getEstado()));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    @GetMapping
    public ResponseEntity<List<PrestamoDto>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Integer id) {
        return service.detalle(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(new Mensaje("Préstamo no encontrado")));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PrestamoDto>> porUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(service.listarPorUsuario(idUsuario));
    }

    @GetMapping("/usuario/{idUsuario}/activos")
    public ResponseEntity<List<PrestamoDto>> activosUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(service.listarActivosPorUsuario(idUsuario));
    }

    @GetMapping("/usuario/{idUsuario}/retrasados")
    public ResponseEntity<List<PrestamoDto>> retrasadosUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(service.listarRetrasadosPorUsuario(idUsuario));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    @GetMapping("/libro/{idLibro}")
    public ResponseEntity<List<PrestamoDto>> porLibro(@PathVariable Integer idLibro) {
        return ResponseEntity.ok(service.listarPorLibro(idLibro));
    }

    // Todos los préstamos activos (bibliotecario/admin)
    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    @GetMapping("/activos")
    public ResponseEntity<List<PrestamoDto>> activos() {
        return ResponseEntity.ok(service.listarActivos());
    }

    // Préstamos activos por libro (bibliotecario/admin)
    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    @GetMapping("/libro/{idLibro}/activos")
    public ResponseEntity<List<PrestamoDto>> activosPorLibro(@PathVariable Integer idLibro) {
        return ResponseEntity.ok(service.listarActivosPorLibro(idLibro));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    @GetMapping("/libro/{idLibro}/activos/count")
    public ResponseEntity<?> activosCountPorLibro(@PathVariable Integer idLibro) {
        int count = service.contarActivosPorLibro(idLibro);
        return ResponseEntity.ok(Map.of("idLibro", idLibro, "prestamosActivos", count));
    }

    static class Mensaje {
        public final String message;
        public Mensaje(String m) { this.message = m; }
    }
}
