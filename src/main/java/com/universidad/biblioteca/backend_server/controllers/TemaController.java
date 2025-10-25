package com.universidad.biblioteca.backend_server.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.biblioteca.backend_server.dto.TemaDto;
import com.universidad.biblioteca.backend_server.requests.ActualizarTemaRequest;
import com.universidad.biblioteca.backend_server.requests.CrearTemaRequest;
import com.universidad.biblioteca.backend_server.services.TemaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/temas")
public class TemaController {
    
    private final TemaService service;

    public TemaController(TemaService service) { this.service = service; }

    @PreAuthorize("hasAuthority('perm:tema.crear')")
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody CrearTemaRequest req) {
        service.crear(req.getNombre());
        return ResponseEntity.created(URI.create("/api/temas"))
                .body(new Mensaje("Tema creado"));
    }

    @PreAuthorize("hasAuthority('perm:tema.actualizar')")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id,
                                        @Valid @RequestBody ActualizarTemaRequest req) {
        service.actualizar(id, req.getNombre());
        return ResponseEntity.ok(new Mensaje("Tema actualizado"));
    }

    @PreAuthorize("hasAuthority('perm:tema.eliminar')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok(new Mensaje("Tema eliminado"));
    }

    @GetMapping
    public ResponseEntity<List<TemaDto>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Integer id) {
        return service.detalle(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(new Mensaje("Tema no encontrado")));
    }

    @GetMapping("/search")
    public ResponseEntity<List<TemaDto>> buscarPorNombre(@RequestParam(name="nombre", required = false) String nombre) {
        return ResponseEntity.ok(service.buscarPorNombre(nombre));
    }

    static class Mensaje {
        public final String message;
        public Mensaje(String m) { this.message = m; }
    }
}
