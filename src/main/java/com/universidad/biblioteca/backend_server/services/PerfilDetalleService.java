package com.universidad.biblioteca.backend_server.services;

import com.universidad.biblioteca.backend_server.dto.PerfilDto;

public interface PerfilDetalleService {
    PerfilDto miPerfilCompleto(Integer idUsuario);
}
