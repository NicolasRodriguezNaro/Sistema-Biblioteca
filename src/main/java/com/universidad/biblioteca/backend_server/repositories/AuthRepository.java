package com.universidad.biblioteca.backend_server.repositories;

import java.time.Instant;
import java.util.Optional;

public interface AuthRepository {
    record UserAuth(Integer idUsuario, String correo, String hash, String estado, boolean correoVerificado, Integer tokenVersion){}

    Optional<UserAuth> findByCorreo(String correoLower);
    void registrarUsuario(Integer idPersona, String correo, String hash, Integer idRol);
    void guardarTokenVerificacion(Integer idUsuario, String token, Instant expiresAt);
    void verificarCorreoPorToken(String token);
    Optional<UserAuth> findById(Integer idUsuario);
}
