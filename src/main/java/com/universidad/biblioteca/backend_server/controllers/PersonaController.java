package com.universidad.biblioteca.backend_server.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.biblioteca.backend_server.requests.ActualizarPersonaRequest;
import com.universidad.biblioteca.backend_server.services.PersonaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/personas")
public class PersonaController {

    private final PersonaService pService;

    public PersonaController(PersonaService pService) { this.pService = pService; }

    @PatchMapping("/{numDocumento}")
    public ResponseEntity<?> actualizar(@PathVariable Integer numDocumento,
                                        @Valid @RequestBody ActualizarPersonaRequest req) {
        pService.actualizar(numDocumento, req);
        return ResponseEntity.ok(new Message("Persona actualizada"));
    }

    static class Message { public final String message; Message(String m){ this.message = m; } }

}
