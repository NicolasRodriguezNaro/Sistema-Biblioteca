package com.universidad.biblioteca.backend_server.services;

import com.universidad.biblioteca.backend_server.responses.AuthTokensResponse;

public interface AuthService {
    void register(Integer idPersona, String correo, String passwordPlano, Integer idRol);
    AuthTokensResponse login(String correo, String passwordPlano);
    AuthTokensResponse refresh(String refreshToken);
    String startEmailVerify(String correo); // devuelve token para que tú lo envíes por email
    void finishEmailVerify(String token);
}
