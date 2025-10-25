package com.universidad.biblioteca.backend_server.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.biblioteca.backend_server.requests.LoginRequest;
import com.universidad.biblioteca.backend_server.requests.RefreshRequest;
import com.universidad.biblioteca.backend_server.requests.RegisterRequest;
import com.universidad.biblioteca.backend_server.requests.VerifyFinishRequest;
import com.universidad.biblioteca.backend_server.requests.VerifyStartRequest;
import com.universidad.biblioteca.backend_server.responses.AuthTokensResponse;
import com.universidad.biblioteca.backend_server.responses.MessageResponse;
import com.universidad.biblioteca.backend_server.services.AuthService;
import com.universidad.biblioteca.backend_server.services.PersonaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;
    private final PersonaService pService;

    public AuthController(AuthService service, PersonaService pService){ 
        this.service = service; 
        this.pService = pService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        pService.registrar(req.getNumDocumento(), req.getNombreUno(), req.getApellidoUno(), req.getDireccion(), req.getTelefono(), req.getTipoDocumento(), req.getNombreDos(), req.getApellidoDos());
        service.register(req.getNumDocumento(), req.getCorreo(), req.getPassword(), req.getIdRol());
        return ResponseEntity.created(URI.create("/auth/register")).body(new MessageResponse("Usuario registrado"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthTokensResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(service.login(req.getCorreo(), req.getPassword()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthTokensResponse> refresh(@Valid @RequestBody RefreshRequest req) {
        return ResponseEntity.ok(service.refresh(req.getRefreshToken()));
    }

    // Enviar email de verificación (devuelve token para que lo uses en tu envío)
    @PostMapping("/verify/start")
    public ResponseEntity<?> startVerify(@Valid @RequestBody VerifyStartRequest req) {
        String token = service.startEmailVerify(req.getCorreo());
        return ResponseEntity.ok(new MessageResponse("Token generado: " + token));
        // En producción no devuelvas el token; acá lo dejamos para pruebas con Postman.
    }

    // Consumir token de verificación (desde el link del correo)
    @PostMapping("/verify/finish")
    public ResponseEntity<?> finishVerify(@Valid @RequestBody VerifyFinishRequest req) {
        service.finishEmailVerify(req.getToken());
        return ResponseEntity.ok(new MessageResponse("Correo verificado"));
    }
}
