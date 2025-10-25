package com.universidad.biblioteca.backend_server.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.biblioteca.backend_server.dto.EjemplarDto;
import com.universidad.biblioteca.backend_server.requests.ActualizarEstadoEjemplarRequest;
import com.universidad.biblioteca.backend_server.services.EjemplarService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/libros/{idLibro}/ejemplares")
public class EjemplarController {

    private final EjemplarService service;
    
    public EjemplarController(EjemplarService service) { this.service = service; }

    // Crear 1 ejemplar (SP: sp_agregar_ejemplar). No requiere body.
    @PreAuthorize("hasAuthority('perm:ejemplar.crear')")
    @PostMapping
    public ResponseEntity<?> agregar(@PathVariable Integer idLibro) {
        Integer numero = service.agregarUno(idLibro);
        return ResponseEntity.created(URI.create("/api/libros/" + idLibro + "/ejemplares/" + numero))
                .body(new Mensaje("Ejemplar creado con numero=" + numero));
    }

    // Borrar ejemplar (SP: sp_eliminar_ejemplar)
    @PreAuthorize("hasAuthority('perm:ejemplar.eliminar')")
    @DeleteMapping("/{numero}")
    public ResponseEntity<?> eliminar(@PathVariable Integer idLibro, @PathVariable Integer numero) {
        service.eliminar(idLibro, numero);
        return ResponseEntity.ok(new Mensaje("Ejemplar eliminado"));
    }

    // Cambiar estado (SP: sp_actualizar_estado_ejemplar)
    @PreAuthorize("hasAuthority('perm:ejemplar.actualizar_estado')")
    @PatchMapping("/{numero}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Integer idLibro,
                                              @PathVariable Integer numero,
                                              @Valid @RequestBody ActualizarEstadoEjemplarRequest req) {
        service.actualizarEstado(idLibro, numero, req.getEstado());
        return ResponseEntity.ok(new Mensaje("Estado actualizado a " + req.getEstado()));
    }

    // Listas y detalle (vista/funciones)
    @PreAuthorize("hasAuthority('perm:ejemplar.ver')")
    @GetMapping
    public ResponseEntity<List<EjemplarDto>> listar(@PathVariable Integer idLibro) {
        return ResponseEntity.ok(service.listarPorLibro(idLibro));
    }

    @PreAuthorize("hasAuthority('perm:ejemplar.ver_disponibles')")
    @GetMapping("/disponibles")
    public ResponseEntity<List<EjemplarDto>> listarDisponibles(@PathVariable Integer idLibro) {
        return ResponseEntity.ok(service.listarDisponibles(idLibro));
    }

    @PreAuthorize("hasAuthority('perm:ejemplar.ver_detalle')")
    @GetMapping("/{numero}")
    public ResponseEntity<?> obtener(@PathVariable Integer idLibro, @PathVariable Integer numero) {
        return service.obtener(idLibro, numero)
            .<ResponseEntity<?>>map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.status(404).body(new Mensaje("Ejemplar no encontrado")));
    }

    static class Mensaje {
        public final String message;
        public Mensaje(String m) { this.message = m; }
    }
}
