package com.universidad.biblioteca.backend_server.services;

import org.springframework.stereotype.Service;

import com.universidad.biblioteca.backend_server.repositories.LibroAutorRepository;

@Service
public class LibroAutorServiceImpl implements LibroAutorService{
    
    private final LibroAutorRepository repo;

    public LibroAutorServiceImpl(LibroAutorRepository repo) { this.repo = repo; }

    @Override public void asignar(Integer idLibro, Integer idAutor) { repo.asignarAutor(idLibro, idAutor); }
    @Override public void quitar(Integer idLibro, Integer idAutor) { repo.quitarAutor(idLibro, idAutor); }

}
