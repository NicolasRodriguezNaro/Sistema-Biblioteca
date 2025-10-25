package com.universidad.biblioteca.backend_server.repositories;

import java.util.List;
import java.util.Optional;

import com.universidad.biblioteca.backend_server.dto.LibroDto;

public interface LibroRepository {

    /** Escritura: usa procedimiento sp_registrar_libro */
    void crear(LibroDto dto);

    /** Escritura: usa procedimiento sp_actualizar_libro */
    void actualizar(Integer idLibro, LibroDto dto);

    /** Escritura: usa procedimiento sp_cambiar_prestabilidad */
    void cambiarPrestable(Integer idLibro, boolean prestable);

    /** Lecturas desde vista/funciones */
    List<LibroDto> listarTodos();
    Optional<LibroDto> buscarPorId(Integer idLibro);
    List<LibroDto> buscarPorTitulo(String filtro);

}
