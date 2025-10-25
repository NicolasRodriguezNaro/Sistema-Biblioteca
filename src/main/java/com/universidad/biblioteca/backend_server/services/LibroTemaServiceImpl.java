package com.universidad.biblioteca.backend_server.services;

import org.springframework.stereotype.Service;

import com.universidad.biblioteca.backend_server.repositories.LibroTemaRepository;

@Service
public class LibroTemaServiceImpl implements LibroTemaService{
    private final LibroTemaRepository repo;

    public LibroTemaServiceImpl(LibroTemaRepository repo) { this.repo = repo; }

    @Override public void asignar(Integer idLibro, Integer idTema) { repo.asignarTema(idLibro, idTema); }
    @Override public void quitar(Integer idLibro, Integer idTema) { repo.quitarTema(idLibro, idTema); }
}
