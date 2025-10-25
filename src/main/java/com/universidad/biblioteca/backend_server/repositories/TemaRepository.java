package com.universidad.biblioteca.backend_server.repositories;

import java.util.List;
import java.util.Optional;

import com.universidad.biblioteca.backend_server.dto.TemaDto;

public interface TemaRepository {
    
    // Escrituras (procedimientos)
    void crear(String nombre);
    void actualizar(Integer id, String nuevoNombre);
    void eliminar(Integer id);

    // Lecturas (vista/funciones)
    List<TemaDto> listarTodos();
    Optional<TemaDto> buscarPorId(Integer id);
    List<TemaDto> buscarPorNombre(String filtro);
}
