package com.universidad.biblioteca.backend_server.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.biblioteca.backend_server.mongo.ResenaDoc;
import com.universidad.biblioteca.backend_server.mongo.ResenaService;

@RestController
@RequestMapping("/api/resenas")
public class ResenaController {
    private final ResenaService service;
    public ResenaController(ResenaService service) { this.service = service; }

    @GetMapping("/libro/{idLibro}")
    public ResponseEntity<List<ResenaDoc>> listar(@PathVariable Integer idLibro) {
        return ResponseEntity.ok(service.listarPorLibro(idLibro));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/libro/{idLibro}")
    public ResponseEntity<ResenaDoc> crear(@PathVariable Integer idLibro,
                                           @AuthenticationPrincipal Integer idUsuario,
                                           @RequestBody CrearResena req) {
        return ResponseEntity.ok(service.crear(idLibro, idUsuario, req.comentario, req.calificacion));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{idResena}/responder")
    public ResponseEntity<?> responder(@PathVariable String idResena,
                                    @AuthenticationPrincipal Integer idUsuario,
                                    @RequestBody RespuestaReq req) {
        service.responder(idResena, idUsuario, req.comentario);
        return ResponseEntity.ok(new Msg("Respuesta agregada"));
    }

    @PreAuthorize("hasAnyRole('ADMIN','BIBLIOTECARIO')")
    @DeleteMapping("/{idResena}")
    public ResponseEntity<?> eliminar(@PathVariable String idResena) {
        service.eliminar(idResena);
        return ResponseEntity.ok(new Msg("Reseña eliminada"));
    }

    // DTOs mínimos
    static class CrearResena { public String comentario; public Integer calificacion; }
    static class RespuestaReq { public String comentario; }
    static class Msg { public final String message; Msg(String m){ this.message = m; } }
}
