package com.universidad.biblioteca.backend_server.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.biblioteca.backend_server.requests.CambiarContrasenaRequest;
import com.universidad.biblioteca.backend_server.requests.CambiarCorreoRequest;
import com.universidad.biblioteca.backend_server.requests.CambiarEstadoRequest;
import com.universidad.biblioteca.backend_server.services.UsuarioAdminService;
import com.universidad.biblioteca.backend_server.services.UsuarioQueryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioAdminController {

    private final UsuarioAdminService service;
    private final UsuarioQueryService queryService;

    public UsuarioAdminController(UsuarioAdminService service, UsuarioQueryService queryService){
        this.service = service;
        this.queryService = queryService;
    }

    @PreAuthorize("hasAuthority('perm:usuario.cambiar_estado')")
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Integer id,
                                           @Valid @RequestBody CambiarEstadoRequest req) {
        try {
            service.cambiarEstado(id, req.getEstado());
            return ResponseEntity.ok(new Mensaje("Estado actualizado"));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMsg("Error al cambiar estado: " + ex.getMessage()));
        }
    }

    @PreAuthorize("hasAuthority('perm:usuario.actualizar_contrasena')")
    @PatchMapping("/{id}/contrasena")
    public ResponseEntity<?> cambiarContrasena(@PathVariable Integer id,
                                               @Valid @RequestBody CambiarContrasenaRequest req) {
        try {
            service.actualizarContrasena(id, req.getNuevaContrasena());
            return ResponseEntity.ok(new Mensaje("Contraseña actualizada"));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMsg("Error al actualizar contraseña: " + ex.getMessage()));
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @PatchMapping("/{id}/correo")
    public ResponseEntity<?> cambiarCorreo(@PathVariable Integer id,
                                        @Valid @RequestBody CambiarCorreoRequest req) {
        try {
            service.actualizarCorreo(id, req.getNuevoCorreo());
            return ResponseEntity.ok(new Mensaje("Correo actualizado (verificación requerida)"));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorMsg("Error al actualizar correo: " + ex.getMessage()));
        }
    }

    // ===== Listados =====
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        return ResponseEntity.ok(queryService.todos());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/activos")
    public ResponseEntity<?> listarActivos() {
        return ResponseEntity.ok(queryService.activos());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/inactivos")
    public ResponseEntity<?> listarInactivos() {
        return ResponseEntity.ok(queryService.inactivos());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/suspendidos")
    public ResponseEntity<?> listarSuspendidos() {
        return ResponseEntity.ok(queryService.suspendidos());
    }

    // pequeñas clases de respuesta
    static class Mensaje { public final String message; Mensaje(String m){ this.message = m; } }
    static class ErrorMsg { public final String error; ErrorMsg(String e){ this.error = e; } }

}
