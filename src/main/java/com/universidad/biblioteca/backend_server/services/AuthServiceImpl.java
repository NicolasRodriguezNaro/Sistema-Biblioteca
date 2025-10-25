package com.universidad.biblioteca.backend_server.services;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.universidad.biblioteca.backend_server.jwt.JwtService;
import com.universidad.biblioteca.backend_server.repositories.AuthRepository;
import com.universidad.biblioteca.backend_server.responses.AuthTokensResponse;

@Service
public class AuthServiceImpl implements AuthService{

    private final AuthRepository repo;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    // TTLs (ajusta por properties si quieres)
    private final long accessTtlSec = 3600;        // 1h
    private final long refreshTtlSec = 1209600;    // 14d
    private final long verifyTtlHours = 24;        // token verificación

    public AuthServiceImpl(AuthRepository repo, PasswordEncoder encoder, JwtService jwt) {
        this.repo = repo; this.encoder = encoder; this.jwt = jwt;
    }

    @Override
    public void register(Integer idPersona, String correo, String passwordPlano, Integer idRol) {
        String hash = encoder.encode(passwordPlano);                 // hash en app
        repo.registrarUsuario(idPersona, correo.trim().toLowerCase(), hash, idRol);
        // opcional: emitir y enviar token verificación aquí
    }

    @Override
    public AuthTokensResponse login(String correo, String passwordPlano) {
        var user = repo.findByCorreo(correo.trim().toLowerCase())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));
        if (!"activo".equalsIgnoreCase(user.estado())) {
            throw new RuntimeException("Usuario no activo");
        }
        if (!encoder.matches(passwordPlano, user.hash())) {
            throw new RuntimeException("Credenciales inválidas");
        }
        String access = jwt.generateAccessToken(user.idUsuario(), user.tokenVersion(), accessTtlSec, null);
        String refresh = jwt.generateAccessToken(user.idUsuario(), user.tokenVersion(), refreshTtlSec, null);
        return new AuthTokensResponse(access, refresh);
    }

    @Override
    public AuthTokensResponse refresh(String refreshToken) {
        Integer idUsuario = jwt.getUserId(refreshToken);
        Integer tvToken = jwt.getTokenVersion(refreshToken);
        var user = repo.findById(idUsuario).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!tvToken.equals(user.tokenVersion())) throw new RuntimeException("Token revocado");
        if (!"activo".equalsIgnoreCase(user.estado())) throw new RuntimeException("Usuario no activo");
        String access = jwt.generateAccessToken(user.idUsuario(), user.tokenVersion(), accessTtlSec, null);
        String refresh = jwt.generateAccessToken(user.idUsuario(), user.tokenVersion(), refreshTtlSec, null);
        return new AuthTokensResponse(access, refresh);
    }

    // ==== Email verify ====
    @Override
    public String startEmailVerify(String correo) {
        var user = repo.findByCorreo(correo.trim().toLowerCase())
                .orElseThrow(() -> new RuntimeException("Correo no registrado"));
        String token = randomUrlToken(48);
        Instant expires = Instant.now().plus(verifyTtlHours, ChronoUnit.HOURS);
        repo.guardarTokenVerificacion(user.idUsuario(), token, expires);
        return token; // Tú lo envías por email con un link
    }

    @Override
    public void finishEmailVerify(String token) {
        repo.verificarCorreoPorToken(token);
    }

    private static String randomUrlToken(int bytes) {
        byte[] buf = new byte[bytes];
        new SecureRandom().nextBytes(buf);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(buf);
    }
}
