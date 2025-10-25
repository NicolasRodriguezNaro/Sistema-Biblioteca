package com.universidad.biblioteca.backend_server.services;

import com.universidad.biblioteca.backend_server.responses.PerfilResponse;

public interface PerfilService {
    PerfilResponse obtenerPerfilCompleto(Integer idUsuario);
}
