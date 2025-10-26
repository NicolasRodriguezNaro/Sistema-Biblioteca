package com.universidad.biblioteca.backend_server.services;

import java.time.LocalDate;
import java.util.List;

import com.universidad.biblioteca.backend_server.dto.MultaDto;

public interface MultaService {
    void crear(Integer idPrestamo, Integer idTipoMulta, LocalDate fechaNullable, String estadoNullable);
    void pagar(Integer idPrestamo, Integer numMulta);
    void anular(Integer idPrestamo, Integer numMulta);

    List<MultaDto> listar();
    List<MultaDto> porPrestamo(Integer idPrestamo);
    List<MultaDto> porUsuario(Integer idUsuario);
    List<MultaDto> porLibro(Integer idLibro);
    List<MultaDto> pendientesPorUsuario(Integer idUsuario);
}
