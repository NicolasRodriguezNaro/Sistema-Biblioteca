package com.universidad.biblioteca.backend_server.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.universidad.biblioteca.backend_server.dto.ReservaDto;

public interface ReservaService {
    void crear(Integer idUsuario, Integer idLibro, LocalDate fechaReserva);
    void cancelar(Integer idReserva);

    List<ReservaDto> listar();
    Optional<ReservaDto> detalle(Integer idReserva);
    List<ReservaDto> listarPorUsuario(Integer idUsuario);
    List<ReservaDto> listarPorLibro(Integer idLibro);
    List<ReservaDto> listarActivasPorUsuario(Integer idUsuario);

    List<ReservaDto> listarActivas();
    List<ReservaDto> listarActivasPorLibro(Integer idLibro);

    int contarActivasPorLibro(Integer idLibro);
}
