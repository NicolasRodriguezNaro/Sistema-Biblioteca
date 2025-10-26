package com.universidad.biblioteca.backend_server.repositories;

import java.time.LocalDate;
import java.util.List;

import com.universidad.biblioteca.backend_server.dto.MultaDto;

public interface MultaRepository {
    // Escrituras (procedimientos)
    void crear(Integer idPrestamo, Integer idTipoMulta, LocalDate fechaNullable, String estadoNullable);
    void pagar(Integer idPrestamo, Integer numMulta);
    void anular(Integer idPrestamo, Integer numMulta);

    // Lecturas
    List<MultaDto> listar();
    List<MultaDto> listarPorPrestamo(Integer idPrestamo);
    List<MultaDto> listarPorUsuario(Integer idUsuario);
    List<MultaDto> listarPorLibro(Integer idLibro);
    List<MultaDto> listarPendientesPorUsuario(Integer idUsuario);
}
