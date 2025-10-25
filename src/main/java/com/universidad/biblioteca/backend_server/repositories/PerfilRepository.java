package com.universidad.biblioteca.backend_server.repositories;

import java.util.Optional;

public interface PerfilRepository {
    Optional<Perfil> findPerfilById(Integer idUsuario);

    // Record EXACTO a lo que tu filtro usa (getters implícitos)
    record Perfil(
        Integer idUsuario,
        String correo,
        String estadoUsuario,
        Integer idRol,
        String nombreRol,
        Boolean correoVerificado,
        Integer tokenVersion
    ) {}
}
