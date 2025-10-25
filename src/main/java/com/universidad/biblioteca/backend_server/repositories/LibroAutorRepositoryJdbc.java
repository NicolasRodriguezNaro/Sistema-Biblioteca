package com.universidad.biblioteca.backend_server.repositories;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LibroAutorRepositoryJdbc implements LibroAutorRepository{

    private final JdbcTemplate jdbc;

    public LibroAutorRepositoryJdbc(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    @Override
    public void asignarAutor(Integer idLibro, Integer idAutor) {
        final String sql = "CALL sp_asignar_autor_libro(?, ?)";
        try {
            jdbc.update(sql, idLibro, idAutor);
        } catch (DataAccessException dae) {
            throw wrap("Error al asignar autor al libro", dae);
        }
    }

    @Override
    public void quitarAutor(Integer idLibro, Integer idAutor) {
        final String sql = "CALL sp_eliminar_autor_libro(?, ?)";
        try {
            jdbc.update(sql, idLibro, idAutor);
        } catch (DataAccessException dae) {
            throw wrap("Error al quitar autor del libro", dae);
        }
    }

    private RuntimeException wrap(String ctx, DataAccessException dae) {
        String msg = dae.getMostSpecificCause() != null
                ? dae.getMostSpecificCause().getMessage()
                : dae.getMessage();
        return new RuntimeException(ctx + ": " + msg, dae);
    }
}
