package com.universidad.biblioteca.backend_server.repositories;

public interface LibroTemaRepository {
    void asignarTema(Integer idLibro, Integer idTema);
    void quitarTema(Integer idLibro, Integer idTema);
}
