package com.universidad.biblioteca.backend_server.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.universidad.biblioteca.backend_server.dto.PrestamoDto;

public interface PrestamoRepository {
    // Escrituras (procedimientos)
    void crear(Integer idUsuario, Integer idLibro, Integer numeroEjemplar,
               LocalDate fechaPrestamo, LocalDate fechaDevolucionProgramada, Integer idReservaNullable);
    void actualizarEstado(Integer idPrestamo, String nuevoEstado, LocalDate fechaDevolucionRealNullable);

    // Lecturas (vista/funciones)
    List<PrestamoDto> listar();
    Optional<PrestamoDto> buscarPorId(Integer idPrestamo);
    List<PrestamoDto> listarPorUsuario(Integer idUsuario);
    List<PrestamoDto> listarPorLibro(Integer idLibro);
    List<PrestamoDto> listarActivosPorUsuario(Integer idUsuario);
    List<PrestamoDto> listarRetrasadosPorUsuario(Integer idUsuario);

    List<PrestamoDto> listarActivos();
    List<PrestamoDto> listarActivosPorLibro(Integer idLibro);

    int contarActivosPorLibro(Integer idLibro);
}
