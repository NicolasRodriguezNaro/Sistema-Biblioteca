package com.universidad.biblioteca.backend_server.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.universidad.biblioteca.backend_server.dto.ReservaDto;

public interface ReservaRepository {
    // Escrituras (procedimientos)
    void crear(Integer idUsuario, Integer idLibro, LocalDate fechaReserva);
    void cancelar(Integer idReserva);

    // Lecturas
    List<ReservaDto> listar();
    Optional<ReservaDto> buscarPorId(Integer idReserva);
    List<ReservaDto> listarPorUsuario(Integer idUsuario);
    List<ReservaDto> listarPorLibro(Integer idLibro);
    List<ReservaDto> listarActivasPorUsuario(Integer idUsuario);

    List<ReservaDto> listarActivas();                 // todas las activas/pendientes
    List<ReservaDto> listarActivasPorLibro(Integer idLibro);

    int contarActivasPorLibro(Integer idLibro);
}
