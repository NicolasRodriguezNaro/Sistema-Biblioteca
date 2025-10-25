package com.universidad.biblioteca.backend_server.repositories;

import java.util.List;
import java.util.Optional;

import com.universidad.biblioteca.backend_server.dto.CategoriaDto;

public interface CategoriaRepository {

     // Escrituras (procedimientos)
    void crear(String nombre);
    void actualizar(Integer id, String nuevoNombre);
    void eliminar(Integer id);

    // Lecturas (vista/funciones)
    List<CategoriaDto> listarTodos();
    Optional<CategoriaDto> buscarPorId(Integer id);
    List<CategoriaDto> buscarPorNombre(String filtro);
}
