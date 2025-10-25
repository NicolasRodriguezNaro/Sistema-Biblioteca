package com.universidad.biblioteca.backend_server.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.universidad.biblioteca.backend_server.dto.EjemplarDto;
import com.universidad.biblioteca.backend_server.repositories.EjemplarRepository;

@Service
public class EjemplarServiceImpl implements EjemplarService{
    private final EjemplarRepository repo;
    public EjemplarServiceImpl(EjemplarRepository repo) { this.repo = repo; }

    @Override public Integer agregarUno(Integer idLibro) { return repo.agregarUno(idLibro); }
    @Override public void eliminar(Integer idLibro, Integer numero) { repo.eliminar(idLibro, numero); }
    @Override public void actualizarEstado(Integer idLibro, Integer numero, String nuevoEstado) { repo.actualizarEstado(idLibro, numero, nuevoEstado); }

    @Override public List<EjemplarDto> listarPorLibro(Integer idLibro) { return repo.listarPorLibro(idLibro); }
    @Override public List<EjemplarDto> listarDisponibles(Integer idLibro) { return repo.listarDisponibles(idLibro); }
    @Override public Optional<EjemplarDto> obtener(Integer idLibro, Integer numero) { return repo.obtener(idLibro, numero); }

}
