package com.universidad.biblioteca.backend_server.repositories;

import java.util.Optional;

import com.universidad.biblioteca.backend_server.dto.PerfilDto;

public interface PerfilDetalleRepository {
    Optional<PerfilDto> findDetalleById(Integer idUsuario);
    Optional<PerfilDto> findDetalleByCorreo(String correo);
}
