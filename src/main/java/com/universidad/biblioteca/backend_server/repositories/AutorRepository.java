package com.universidad.biblioteca.backend_server.repositories;

import java.util.List;
import java.util.Optional;

import com.universidad.biblioteca.backend_server.dto.AutorDto;

public interface AutorRepository {
    // Escrituras (procedimientos)
    void crear(String nombre);
    void actualizar(Integer id, String nuevoNombre);
    void eliminar(Integer id);

    // Lecturas (vista/funciones)
    List<AutorDto> listarTodos();
    Optional<AutorDto> buscarPorId(Integer id);
    List<AutorDto> buscarPorNombre(String filtro);
}
