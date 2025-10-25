package com.universidad.biblioteca.backend_server.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.biblioteca.backend_server.requests.AsignarTemaRequest;
import com.universidad.biblioteca.backend_server.services.LibroTemaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/libros/{idLibro}/temas")
public class LibroTemaController {

    private final LibroTemaService service;

    public LibroTemaController(LibroTemaService service) { this.service = service; }

    // Asignar tema al libro (SP: sp_asignar_tema_libro)
    @PreAuthorize("hasAuthority('perm:libro_tema.asignar')")
    @PostMapping
    public ResponseEntity<?> asignar(@PathVariable Integer idLibro,
                                     @Valid @RequestBody AsignarTemaRequest req) {
        service.asignar(idLibro, req.getIdTema());
        return ResponseEntity.ok(new Mensaje("Tema asignado al libro"));
    }

    // Quitar tema del libro (SP: sp_eliminar_tema_libro)
    @PreAuthorize("hasAuthority('perm:libro_tema.eliminar')")
    @DeleteMapping("/{idTema}")
    public ResponseEntity<?> quitar(@PathVariable Integer idLibro,
                                    @PathVariable Integer idTema) {
        service.quitar(idLibro, idTema);
        return ResponseEntity.ok(new Mensaje("Tema quitado del libro"));
    }

    static class Mensaje {
        public final String message;
        public Mensaje(String m) { this.message = m; }
    }
}
