package com.universidad.biblioteca.backend_server.services;

import java.util.List;
import java.util.Optional;

import com.universidad.biblioteca.backend_server.dto.AutorDto;

public interface AutorService {
    void crear(String nombre);
    void actualizar(Integer id, String nuevoNombre);
    void eliminar(Integer id);

    List<AutorDto> listar();
    Optional<AutorDto> detalle(Integer id);
    List<AutorDto> buscarPorNombre(String nombre);
}
