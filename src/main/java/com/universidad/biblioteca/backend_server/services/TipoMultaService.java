package com.universidad.biblioteca.backend_server.services;

import java.math.BigDecimal;
import java.util.List;

import com.universidad.biblioteca.backend_server.dto.TipoMultaDto;

public interface TipoMultaService {
    void crear(String nombre, BigDecimal monto);
    List<TipoMultaDto> listar();
}
