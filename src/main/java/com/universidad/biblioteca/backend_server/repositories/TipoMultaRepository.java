package com.universidad.biblioteca.backend_server.repositories;

import java.util.List;

import com.universidad.biblioteca.backend_server.dto.TipoMultaDto;

public interface TipoMultaRepository {
    void crear(String nombre, java.math.BigDecimal monto);   // sp_crear_tipo_multa
    List<TipoMultaDto> listar();
}
