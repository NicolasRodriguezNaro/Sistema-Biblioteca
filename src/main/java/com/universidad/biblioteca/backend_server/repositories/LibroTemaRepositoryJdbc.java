package com.universidad.biblioteca.backend_server.repositories;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LibroTemaRepositoryJdbc implements LibroTemaRepository{
    
    private final JdbcTemplate jdbc;

    public LibroTemaRepositoryJdbc(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    @Override
    public void asignarTema(Integer idLibro, Integer idTema) {
        final String sql = "CALL sp_asignar_tema_libro(?, ?)";
        try {
            jdbc.update(sql, idLibro, idTema);
        } catch (DataAccessException dae) {
            throw wrap("Error al asignar tema al libro", dae);
        }
    }

    @Override
    public void quitarTema(Integer idLibro, Integer idTema) {
        final String sql = "CALL sp_eliminar_tema_libro(?, ?)";
        try {
            jdbc.update(sql, idLibro, idTema);
        } catch (DataAccessException dae) {
            throw wrap("Error al quitar tema del libro", dae);
        }
    }

    private RuntimeException wrap(String ctx, DataAccessException dae) {
        String msg = dae.getMostSpecificCause() != null
                ? dae.getMostSpecificCause().getMessage()
                : dae.getMessage();
        return new RuntimeException(ctx + ": " + msg, dae);
    }
}
