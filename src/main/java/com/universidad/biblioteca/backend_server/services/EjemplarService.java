package com.universidad.biblioteca.backend_server.services;

import java.util.List;
import java.util.Optional;

import com.universidad.biblioteca.backend_server.dto.EjemplarDto;

public interface EjemplarService {

    Integer agregarUno(Integer idLibro);                    // o void
    void eliminar(Integer idLibro, Integer numero);
    void actualizarEstado(Integer idLibro, Integer numero, String nuevoEstado);

    List<EjemplarDto> listarPorLibro(Integer idLibro);
    List<EjemplarDto> listarDisponibles(Integer idLibro);
    Optional<EjemplarDto> obtener(Integer idLibro, Integer numero);

}
