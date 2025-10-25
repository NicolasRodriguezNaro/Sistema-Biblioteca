package com.universidad.biblioteca.backend_server.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.universidad.biblioteca.backend_server.dto.LibroDto;
import com.universidad.biblioteca.backend_server.repositories.LibroRepository;

@Service
public class LibroServiceImpl implements LibroService{

    private final LibroRepository repo;

    public LibroServiceImpl(LibroRepository repo) {
        this.repo = repo;
    }

    @Override public void crear(LibroDto dto) { repo.crear(dto); }
    @Override public void actualizar(Integer idLibro, LibroDto dto) { repo.actualizar(idLibro, dto); }
    @Override public void cambiarPrestable(Integer idLibro, boolean prestable) { repo.cambiarPrestable(idLibro, prestable); }
    @Override public List<LibroDto> listar() { return repo.listarTodos(); }
    @Override public Optional<LibroDto> detalle(Integer idLibro) { return repo.buscarPorId(idLibro); }
    @Override
    public List<LibroDto> buscarPorTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) return List.of();
        return repo.buscarPorTitulo(titulo.trim());
    }
}
