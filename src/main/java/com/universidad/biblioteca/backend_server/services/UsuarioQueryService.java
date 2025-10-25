package com.universidad.biblioteca.backend_server.services;

import java.util.List;

import com.universidad.biblioteca.backend_server.repositories.UsuarioQueryRepository.UsuarioListado;

public interface UsuarioQueryService {
    List<UsuarioListado> todos();
    List<UsuarioListado> activos();
    List<UsuarioListado> inactivos();
    List<UsuarioListado> suspendidos();
}
