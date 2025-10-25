package com.universidad.biblioteca.backend_server.services;

import java.util.List;
import java.util.Optional;

import com.universidad.biblioteca.backend_server.dto.LibroDto;

public interface LibroService {

    void crear(LibroDto dto);
    void actualizar(Integer idLibro, LibroDto dto);
    void cambiarPrestable(Integer idLibro, boolean prestable);
    List<LibroDto> listar();
    Optional<LibroDto> detalle(Integer idLibro);
    List<LibroDto> buscarPorTitulo(String titulo);
}
