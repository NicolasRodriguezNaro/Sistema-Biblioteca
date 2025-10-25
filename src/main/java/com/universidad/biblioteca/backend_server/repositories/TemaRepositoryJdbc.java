package com.universidad.biblioteca.backend_server.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.universidad.biblioteca.backend_server.dto.TemaDto;

@Repository
public class TemaRepositoryJdbc implements TemaRepository{
    
    private final JdbcTemplate jdbc;

    public TemaRepositoryJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<TemaDto> MAPPER = new RowMapper<>() {
        @Override
        public TemaDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new TemaDto(
                rs.getInt("pk_idtema"),
                rs.getString("nombre_tema")
            );
        }
    };

    // ---- Escrituras (procedimientos) ----
    @Override
    public void crear(String nombre) {
        final String sql = "CALL sp_crear_tema(?)";
        try {
            jdbc.update(sql, nombre);
        } catch (DataAccessException dae) {
            throw wrap("Error al crear tema", dae);
        }
    }

    @Override
    public void actualizar(Integer id, String nuevoNombre) {
        final String sql = "CALL sp_actualizar_tema(?, ?)";
        try {
            jdbc.update(sql, id, nuevoNombre);
        } catch (DataAccessException dae) {
            throw wrap("Error al actualizar tema", dae);
        }
    }

    @Override
    public void eliminar(Integer id) {
        final String sql = "CALL sp_eliminar_tema(?)";
        try {
            jdbc.update(sql, id);
        } catch (DataAccessException dae) {
            throw wrap("Error al eliminar tema", dae);
        }
    }

    // ---- Lecturas (vista/funciones) ----
    @Override
    public List<TemaDto> listarTodos() {
        final String sql = "SELECT * FROM v_temas";
        return jdbc.query(sql, MAPPER);
    }

    @Override
    public Optional<TemaDto> buscarPorId(Integer id) {
        final String sql = "SELECT * FROM fn_tema_por_id(?)";
        List<TemaDto> list = jdbc.query(sql, MAPPER, id);
        return list.stream().findFirst();
    }

    @Override
    public List<TemaDto> buscarPorNombre(String filtro) {
        final String sql = "SELECT * FROM fn_temas_por_nombre(?)";
        return jdbc.query(sql, MAPPER, filtro);
    }

    private RuntimeException wrap(String ctx, DataAccessException dae) {
        String msg = dae.getMostSpecificCause() != null
                ? dae.getMostSpecificCause().getMessage()
                : dae.getMessage();
        return new RuntimeException(ctx + ": " + msg, dae);
    }
}
