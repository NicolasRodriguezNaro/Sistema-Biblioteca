package com.universidad.biblioteca.backend_server.services;

import java.util.List;
import java.util.Optional;

import com.universidad.biblioteca.backend_server.dto.TemaDto;

public interface TemaService {
    void crear(String nombre);
    void actualizar(Integer id, String nuevoNombre);
    void eliminar(Integer id);

    List<TemaDto> listar();
    Optional<TemaDto> detalle(Integer id);
    List<TemaDto> buscarPorNombre(String nombre);
}
