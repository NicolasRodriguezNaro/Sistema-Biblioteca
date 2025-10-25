package com.universidad.biblioteca.backend_server.services;

import org.springframework.stereotype.Service;

import com.universidad.biblioteca.backend_server.dto.PerfilDto;
import com.universidad.biblioteca.backend_server.repositories.PerfilDetalleRepository;

@Service
public class PerfilDetalleServiceImpl implements PerfilDetalleService{
    
    private final PerfilDetalleRepository repo;

    public PerfilDetalleServiceImpl(PerfilDetalleRepository repo) {
        this.repo = repo;
    }

    @Override
    public PerfilDto miPerfilCompleto(Integer idUsuario) {
        return repo.findDetalleById(idUsuario)
                   .orElseThrow(() -> new RuntimeException("No existe usuario con id " + idUsuario));
    }
}
