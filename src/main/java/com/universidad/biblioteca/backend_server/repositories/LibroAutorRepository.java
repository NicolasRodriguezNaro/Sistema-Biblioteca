package com.universidad.biblioteca.backend_server.repositories;

public interface LibroAutorRepository {
    void asignarAutor(Integer idLibro, Integer idAutor);
    void quitarAutor(Integer idLibro, Integer idAutor);
}
