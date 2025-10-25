package com.universidad.biblioteca.backend_server.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.biblioteca.backend_server.requests.AsignarAutorRequest;
import com.universidad.biblioteca.backend_server.services.LibroAutorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/libros/{idLibro}/autores")
public class LibroAutorController {
    
    private final LibroAutorService service;

    public LibroAutorController(LibroAutorService service) { this.service = service; }

    // Asignar autor al libro (SP: sp_asignar_autor_libro)
    @PreAuthorize("hasAuthority('perm:autor_libro.asignar')")
    @PostMapping
    public ResponseEntity<?> asignar(@PathVariable Integer idLibro,
                                     @Valid @RequestBody AsignarAutorRequest req) {
        service.asignar(idLibro, req.getIdAutor());
        return ResponseEntity.ok(new Mensaje("Autor asignado al libro"));
    }

    // Quitar autor del libro (SP: sp_eliminar_autor_libro)
    @PreAuthorize("hasAuthority('perm:autor_libro.eliminar')")
    @DeleteMapping("/{idAutor}")
    public ResponseEntity<?> quitar(@PathVariable Integer idLibro,
                                    @PathVariable Integer idAutor) {
        service.quitar(idLibro, idAutor);
        return ResponseEntity.ok(new Mensaje("Autor quitado del libro"));
    }

    static class Mensaje {
        public final String message;
        public Mensaje(String m) { this.message = m; }
    }
}
