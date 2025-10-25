package com.universidad.biblioteca.backend_server.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.universidad.biblioteca.backend_server.dto.CategoriaDto;
import com.universidad.biblioteca.backend_server.repositories.CategoriaRepository;

@Service
public class CategoriaServiceImpl implements CategoriaService{
    
    private final CategoriaRepository repo;

    public CategoriaServiceImpl(CategoriaRepository repo) { this.repo = repo; }

    @Override public void crear(String nombre) { repo.crear(nombre); }

    @Override public void actualizar(Integer id, String nuevoNombre) { repo.actualizar(id, nuevoNombre); }

    @Override public void eliminar(Integer id) { repo.eliminar(id); }

    @Override public List<CategoriaDto> listar() { return repo.listarTodos(); }

    @Override public Optional<CategoriaDto> detalle(Integer id) { return repo.buscarPorId(id); }

    @Override
    public List<CategoriaDto> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) return List.of();
        return repo.buscarPorNombre(nombre.trim());
    }

}
