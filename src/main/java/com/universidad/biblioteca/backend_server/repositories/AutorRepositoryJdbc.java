package com.universidad.biblioteca.backend_server.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.universidad.biblioteca.backend_server.dto.AutorDto;

@Repository
public class AutorRepositoryJdbc implements AutorRepository{

    private final JdbcTemplate jdbc;

    public AutorRepositoryJdbc(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private static final RowMapper<AutorDto> MAPPER = new RowMapper<>() {
        @Override
        public AutorDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new AutorDto(
                rs.getInt("pk_idautor"),
                rs.getString("nombre_autor")
            );
        }
    };

    // ---- Escrituras (procedimientos) ----
    @Override
    public void crear(String nombre) {
        final String sql = "CALL sp_crear_autor(?)";
        try {
            jdbc.update(sql, nombre);
        } catch (DataAccessException dae) {
            throw wrap("Error al crear autor", dae);
        }
    }

    @Override
    public void actualizar(Integer id, String nuevoNombre) {
        final String sql = "CALL sp_actualizar_autor(?, ?)";
        try {
            jdbc.update(sql, id, nuevoNombre);
        } catch (DataAccessException dae) {
            throw wrap("Error al actualizar autor", dae);
        }
    }

    @Override
    public void eliminar(Integer id) {
        final String sql = "CALL sp_eliminar_autor(?)";
        try {
            jdbc.update(sql, id);
        } catch (DataAccessException dae) {
            throw wrap("Error al eliminar autor", dae);
        }
    }

    // ---- Lecturas (vista/funciones) ----
    @Override
    public List<AutorDto> listarTodos() {
        final String sql = "SELECT * FROM v_autores";
        return jdbc.query(sql, MAPPER);
    }

    @Override
    public Optional<AutorDto> buscarPorId(Integer id) {
        final String sql = "SELECT * FROM fn_autor_por_id(?)";
        List<AutorDto> list = jdbc.query(sql, MAPPER, id);
        return list.stream().findFirst();
    }

    @Override
    public List<AutorDto> buscarPorNombre(String filtro) {
        final String sql = "SELECT * FROM fn_autores_por_nombre(?)";
        return jdbc.query(sql, MAPPER, filtro);
    }

    private RuntimeException wrap(String ctx, DataAccessException dae) {
        String msg = dae.getMostSpecificCause() != null
                ? dae.getMostSpecificCause().getMessage()
                : dae.getMessage();
        return new RuntimeException(ctx + ": " + msg, dae);
    }
}
