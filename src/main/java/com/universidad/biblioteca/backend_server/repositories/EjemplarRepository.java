package com.universidad.biblioteca.backend_server.repositories;

import java.util.List;
import java.util.Optional;

import com.universidad.biblioteca.backend_server.dto.EjemplarDto;

public interface EjemplarRepository {
    // Escritura (procedimientos)
    Integer agregarUno(Integer idLibro);                     // devuelve el nuevo numero (opcional, ver nota)
    void eliminar(Integer idLibro, Integer numero);
    void actualizarEstado(Integer idLibro, Integer numero, String nuevoEstado);

    // Lectura (vista/funciones)
    List<EjemplarDto> listarPorLibro(Integer idLibro);
    List<EjemplarDto> listarDisponibles(Integer idLibro);
    Optional<EjemplarDto> obtener(Integer idLibro, Integer numero);
}
