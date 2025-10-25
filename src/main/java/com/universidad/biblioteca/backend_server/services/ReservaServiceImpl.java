package com.universidad.biblioteca.backend_server.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.universidad.biblioteca.backend_server.dto.ReservaDto;
import com.universidad.biblioteca.backend_server.repositories.ReservaRepository;

@Service
public class ReservaServiceImpl implements ReservaService{
    private final ReservaRepository repo;

    public ReservaServiceImpl(ReservaRepository repo) { this.repo = repo; }

    @Override public void crear(Integer idUsuario, Integer idLibro, LocalDate fechaReserva) { repo.crear(idUsuario, idLibro, fechaReserva); }
    @Override public void cancelar(Integer idReserva) { repo.cancelar(idReserva); }

    @Override public List<ReservaDto> listar() { return repo.listar(); }
    @Override public Optional<ReservaDto> detalle(Integer idReserva) { return repo.buscarPorId(idReserva); }
    @Override public List<ReservaDto> listarPorUsuario(Integer idUsuario) { return repo.listarPorUsuario(idUsuario); }
    @Override public List<ReservaDto> listarPorLibro(Integer idLibro) { return repo.listarPorLibro(idLibro); }
    @Override public List<ReservaDto> listarActivasPorUsuario(Integer idUsuario) { return repo.listarActivasPorUsuario(idUsuario); }

    @Override public List<ReservaDto> listarActivas() { return repo.listarActivas(); }
    @Override public List<ReservaDto> listarActivasPorLibro(Integer idLibro) { return repo.listarActivasPorLibro(idLibro); }
    @Override public int contarActivasPorLibro(Integer idLibro) { return repo.contarActivasPorLibro(idLibro); }
}
