package com.universidad.biblioteca.backend_server.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.universidad.biblioteca.backend_server.dto.TemaDto;
import com.universidad.biblioteca.backend_server.repositories.TemaRepository;

@Service
public class TemaServiceImpl implements TemaService{
    
    private final TemaRepository repo;

    public TemaServiceImpl(TemaRepository repo) { this.repo = repo; }

    @Override public void crear(String nombre) { repo.crear(nombre); }
    @Override public void actualizar(Integer id, String nuevoNombre) { repo.actualizar(id, nuevoNombre); }
    @Override public void eliminar(Integer id) { repo.eliminar(id); }

    @Override public List<TemaDto> listar() { return repo.listarTodos(); }
    @Override public Optional<TemaDto> detalle(Integer id) { return repo.buscarPorId(id); }

    @Override
    public List<TemaDto> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) return List.of();
        return repo.buscarPorNombre(nombre.trim());
    }
}
