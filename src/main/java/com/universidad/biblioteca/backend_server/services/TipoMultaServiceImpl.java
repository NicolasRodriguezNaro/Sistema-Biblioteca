package com.universidad.biblioteca.backend_server.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.universidad.biblioteca.backend_server.dto.TipoMultaDto;
import com.universidad.biblioteca.backend_server.repositories.TipoMultaRepository;

@Service
public class TipoMultaServiceImpl implements TipoMultaService{
    private final TipoMultaRepository repo;
    public TipoMultaServiceImpl(TipoMultaRepository repo) { this.repo = repo; }

    @Override public void crear(String nombre, BigDecimal monto) { repo.crear(nombre, monto); }
    @Override public List<TipoMultaDto> listar() { return repo.listar(); }
}
