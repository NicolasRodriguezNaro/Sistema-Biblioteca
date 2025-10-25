package com.universidad.biblioteca.backend_server.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.biblioteca.backend_server.dto.LibroDto;
import com.universidad.biblioteca.backend_server.requests.ActualizarLibroRequest;
import com.universidad.biblioteca.backend_server.requests.CambiarPrestableRequest;
import com.universidad.biblioteca.backend_server.requests.CrearLibroRequest;
import com.universidad.biblioteca.backend_server.services.LibroService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroService service;

    public LibroController(LibroService service) {
        this.service = service;
    }

    @PreAuthorize("hasAuthority('perm:libro.crear')")
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody CrearLibroRequest req) {
        LibroDto dto = new LibroDto(
                null,
                req.getIsbn(),
                req.getTitulo(),
                req.getDescripcion(),
                null,  // cantidadEjemplares la maneja BD (triggers)
                req.getEditorial(),
                req.getAnioPublicacion(),
                req.getPrestable(),
                req.getIdCategoria(),
                null   // nombreCategoria viene en lecturas
        );
        service.crear(dto);
        // No tenemos ID devuelto por el SP; respondemos 201 sin Location específica
        return ResponseEntity.created(URI.create("/api/libros"))
                .body(new Mensaje("Libro creado"));
    }

    @PreAuthorize("hasAuthority('perm:libro.actualizar')")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id,
                                        @Valid @RequestBody ActualizarLibroRequest req) {
        LibroDto dto = new LibroDto(
                id,
                req.getIsbn(),
                req.getTitulo(),
                req.getDescripcion(),
                null,
                req.getEditorial(),
                req.getAnioPublicacion(),
                req.getPrestable(),
                req.getIdCategoria(),
                null
        );
        service.actualizar(id, dto);
        return ResponseEntity.ok(new Mensaje("Libro actualizado"));
    }

    @PreAuthorize("hasAuthority('perm:libro.cambiar_prestabilidad')")
    @PatchMapping("/{id}/prestable")
    public ResponseEntity<?> cambiarPrestable(@PathVariable Integer id,
                                              @Valid @RequestBody CambiarPrestableRequest req) {
        service.cambiarPrestable(id, req.getPrestable());
        return ResponseEntity.ok(new Mensaje("Prestabilidad actualizada"));
    }

    @GetMapping
    public ResponseEntity<List<LibroDto>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Integer id) {
        return service.detalle(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404)
                        .body(new Mensaje("Libro no encontrado")));
    }

    @GetMapping("/search")
    public ResponseEntity<List<LibroDto>> buscarPorTitulo(@RequestParam(name = "titulo", required = false) String titulo) {
        return ResponseEntity.ok(service.buscarPorTitulo(titulo));
    }

    // DTO simple para respuestas de mensaje
    static class Mensaje {
        public final String message;
        public Mensaje(String m) { this.message = m; }
    }
}
