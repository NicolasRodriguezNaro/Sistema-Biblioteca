package com.universidad.biblioteca.backend_server.services;

public interface LibroAutorService {
    void asignar(Integer idLibro, Integer idAutor);
    void quitar(Integer idLibro, Integer idAutor);
}
