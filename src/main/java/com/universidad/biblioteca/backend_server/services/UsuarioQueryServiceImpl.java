package com.universidad.biblioteca.backend_server.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.universidad.biblioteca.backend_server.repositories.UsuarioQueryRepository;
import com.universidad.biblioteca.backend_server.repositories.UsuarioQueryRepository.UsuarioListado;

@Service
public class UsuarioQueryServiceImpl implements UsuarioQueryService{
    
    private final UsuarioQueryRepository repo;
    public UsuarioQueryServiceImpl(UsuarioQueryRepository repo) { this.repo = repo; }

    @Override public List<UsuarioListado> todos()       { return repo.listarTodos(); }
    @Override public List<UsuarioListado> activos()     { return repo.listarActivos(); }
    @Override public List<UsuarioListado> inactivos()   { return repo.listarInactivos(); }
    @Override public List<UsuarioListado> suspendidos() { return repo.listarSuspendidos(); }
}
