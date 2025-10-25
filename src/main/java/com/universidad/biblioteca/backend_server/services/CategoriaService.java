package com.universidad.biblioteca.backend_server.services;

import java.util.List;
import java.util.Optional;

import com.universidad.biblioteca.backend_server.dto.CategoriaDto;

public interface CategoriaService {
    
    void crear(String nombre);
    void actualizar(Integer id, String nuevoNombre);
    void eliminar(Integer id);

    List<CategoriaDto> listar();
    Optional<CategoriaDto> detalle(Integer id);
    List<CategoriaDto> buscarPorNombre(String nombre);
}
