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

import com.universidad.biblioteca.backend_server.dto.CategoriaDto;
import com.universidad.biblioteca.backend_server.requests.ActualizarCategoriaRequest;
import com.universidad.biblioteca.backend_server.requests.CrearCategoriaRequest;
import com.universidad.biblioteca.backend_server.services.CategoriaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    
    private final CategoriaService service;

    public CategoriaController(CategoriaService service) { this.service = service; }

    @PreAuthorize("hasAuthority('perm:categoria.crear')")
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody CrearCategoriaRequest req) {
        service.crear(req.getNombre());
        return ResponseEntity.created(URI.create("/api/categorias"))
                .body(new Mensaje("Categoría creada"));
    }

    @PreAuthorize("hasAuthority('perm:categoria.actualizar')")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id,
                                        @Valid @RequestBody ActualizarCategoriaRequest req) {
        service.actualizar(id, req.getNombre());
        return ResponseEntity.ok(new Mensaje("Categoría actualizada"));
    }

    @PreAuthorize("hasAuthority('perm:categoria.eliminar')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok(new Mensaje("Categoría eliminada"));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDto>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Integer id) {
        return service.detalle(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(new Mensaje("Categoría no encontrada")));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CategoriaDto>> buscarPorNombre(@RequestParam(name="nombre", required = false) String nombre) {
        return ResponseEntity.ok(service.buscarPorNombre(nombre));
    }

    static class Mensaje {
        public final String message;
        public Mensaje(String m) { this.message = m; }
    }
}
