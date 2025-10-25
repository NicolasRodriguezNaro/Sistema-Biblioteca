package com.universidad.biblioteca.backend_server.services;

public interface LibroTemaService {
    void asignar(Integer idLibro, Integer idTema);
    void quitar(Integer idLibro, Integer idTema);
}
