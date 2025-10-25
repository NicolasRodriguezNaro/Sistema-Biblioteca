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

import com.universidad.biblioteca.backend_server.dto.AutorDto;
import com.universidad.biblioteca.backend_server.requests.ActualizarAutorRequest;
import com.universidad.biblioteca.backend_server.requests.CrearAutorRequest;
import com.universidad.biblioteca.backend_server.services.AutorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/autores")
public class AutorController {
    private final AutorService service;

    public AutorController(AutorService service) { this.service = service; }

    @PreAuthorize("hasAuthority('perm:autor.crear')")
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody CrearAutorRequest req) {
        service.crear(req.getNombre());
        return ResponseEntity.created(URI.create("/api/autores"))
                .body(new Mensaje("Autor creado"));
    }

    @PreAuthorize("hasAuthority('perm:autor.actualizar')")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id,
                                        @Valid @RequestBody ActualizarAutorRequest req) {
        service.actualizar(id, req.getNombre());
        return ResponseEntity.ok(new Mensaje("Autor actualizado"));
    }

    @PreAuthorize("hasAuthority('perm:autor.eliminar')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok(new Mensaje("Autor eliminado"));
    }

    @GetMapping
    public ResponseEntity<List<AutorDto>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Integer id) {
        return service.detalle(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(new Mensaje("Autor no encontrado")));
    }

    @GetMapping("/search")
    public ResponseEntity<List<AutorDto>> buscarPorNombre(@RequestParam(name="nombre", required = false) String nombre) {
        return ResponseEntity.ok(service.buscarPorNombre(nombre));
    }

    static class Mensaje {
        public final String message;
        public Mensaje(String m) { this.message = m; }
    }
}
