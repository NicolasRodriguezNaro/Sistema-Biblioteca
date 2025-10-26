package com.universidad.biblioteca.backend_server.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.biblioteca.backend_server.dto.MultaDto;
import com.universidad.biblioteca.backend_server.requests.CrearMultaRequest;
import com.universidad.biblioteca.backend_server.services.MultaService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/multas")
public class MultaController {

    private final MultaService service;
    public MultaController(MultaService service) { this.service = service; }

    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody CrearMultaRequest req) {
        service.crear(req.getIdPrestamo(), req.getIdTipoMulta(), req.getFecha(), req.getEstado());
        return ResponseEntity.ok(new Mensaje("Multa creada"));
    }

    @PatchMapping("/prestamo/{idPrestamo}/num/{numMulta}/pagar")
    public ResponseEntity<?> pagar(@PathVariable @Min(1) Integer idPrestamo,
                                   @PathVariable @Min(1) Integer numMulta) {
        service.pagar(idPrestamo, numMulta);
        return ResponseEntity.ok(new Mensaje("Multa pagada"));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    @PatchMapping("/prestamo/{idPrestamo}/num/{numMulta}/anular")
    public ResponseEntity<?> anular(@PathVariable @Min(1) Integer idPrestamo,
                                    @PathVariable @Min(1) Integer numMulta) {
        service.anular(idPrestamo, numMulta);
        return ResponseEntity.ok(new Mensaje("Multa anulada"));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    @GetMapping
    public ResponseEntity<List<MultaDto>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    @GetMapping("/prestamo/{idPrestamo}")
    public ResponseEntity<List<MultaDto>> porPrestamo(@PathVariable Integer idPrestamo) {
        return ResponseEntity.ok(service.porPrestamo(idPrestamo));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<MultaDto>> porUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(service.porUsuario(idUsuario));
    }

    @GetMapping("/usuario/{idUsuario}/pendientes")
    public ResponseEntity<List<MultaDto>> pendientesUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(service.pendientesPorUsuario(idUsuario));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    @GetMapping("/libro/{idLibro}")
    public ResponseEntity<List<MultaDto>> porLibro(@PathVariable Integer idLibro) {
        return ResponseEntity.ok(service.porLibro(idLibro));
    }

    static class Mensaje { public final String message; public Mensaje(String m){ this.message = m; } }
}
