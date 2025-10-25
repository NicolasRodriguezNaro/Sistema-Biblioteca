package com.universidad.biblioteca.backend_server.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.universidad.biblioteca.backend_server.dto.PrestamoDto;
import com.universidad.biblioteca.backend_server.repositories.PrestamoRepository;

@Service
public class PrestamoServiceImpl implements PrestamoService{

    private final PrestamoRepository repo;
    public PrestamoServiceImpl(PrestamoRepository repo) { this.repo = repo; }

    @Override
    public void crear(Integer idUsuario, Integer idLibro, Integer numeroEjemplar,
                      LocalDate fechaPrestamo, LocalDate fechaDevolucionProgramada, Integer idReservaNullable) {
        repo.crear(idUsuario, idLibro, numeroEjemplar, fechaPrestamo, fechaDevolucionProgramada, idReservaNullable);
    }

    @Override
    public void actualizarEstado(Integer idPrestamo, String nuevoEstado, LocalDate fechaDevolucionRealNullable) {
        repo.actualizarEstado(idPrestamo, nuevoEstado, fechaDevolucionRealNullable);
    }

    @Override public List<PrestamoDto> listar() { return repo.listar(); }
    @Override public Optional<PrestamoDto> detalle(Integer idPrestamo) { return repo.buscarPorId(idPrestamo); }
    @Override public List<PrestamoDto> listarPorUsuario(Integer idUsuario) { return repo.listarPorUsuario(idUsuario); }
    @Override public List<PrestamoDto> listarPorLibro(Integer idLibro) { return repo.listarPorLibro(idLibro); }
    @Override public List<PrestamoDto> listarActivosPorUsuario(Integer idUsuario) { return repo.listarActivosPorUsuario(idUsuario); }
    @Override public List<PrestamoDto> listarRetrasadosPorUsuario(Integer idUsuario) { return repo.listarRetrasadosPorUsuario(idUsuario); }

    @Override public List<PrestamoDto> listarActivos() { return repo.listarActivos(); }

    @Override public List<PrestamoDto> listarActivosPorLibro(Integer idLibro) {
        return repo.listarActivosPorLibro(idLibro);
    }

    @Override public int contarActivosPorLibro(Integer idLibro) { return repo.contarActivosPorLibro(idLibro); }
}
