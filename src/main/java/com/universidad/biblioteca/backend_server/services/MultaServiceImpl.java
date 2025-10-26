package com.universidad.biblioteca.backend_server.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.universidad.biblioteca.backend_server.dto.MultaDto;
import com.universidad.biblioteca.backend_server.repositories.MultaRepository;

@Service
public class MultaServiceImpl implements MultaService{
    private final MultaRepository repo;
    public MultaServiceImpl(MultaRepository repo) { this.repo = repo; }

    @Override
    public void crear(Integer idPrestamo, Integer idTipoMulta, java.time.LocalDate fechaNullable, String estadoNullable) {
        repo.crear(idPrestamo, idTipoMulta, fechaNullable, estadoNullable == null ? null : estadoNullable.trim().toLowerCase());
    }
    @Override public void pagar(Integer idPrestamo, Integer numMulta) { repo.pagar(idPrestamo, numMulta); }
    @Override public void anular(Integer idPrestamo, Integer numMulta) { repo.anular(idPrestamo, numMulta); }

    @Override public List<MultaDto> listar() { return repo.listar(); }
    @Override public List<MultaDto> porPrestamo(Integer idPrestamo) { return repo.listarPorPrestamo(idPrestamo); }
    @Override public List<MultaDto> porUsuario(Integer idUsuario) { return repo.listarPorUsuario(idUsuario); }
    @Override public List<MultaDto> porLibro(Integer idLibro) { return repo.listarPorLibro(idLibro); }
    @Override public List<MultaDto> pendientesPorUsuario(Integer idUsuario) { return repo.listarPendientesPorUsuario(idUsuario); }
}
