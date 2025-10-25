package com.universidad.biblioteca.backend_server.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.universidad.biblioteca.backend_server.dto.CategoriaDto;

@Repository
public class CategoriaRepositoryJdbc implements CategoriaRepository{
    
    private final JdbcTemplate jdbc;

    public CategoriaRepositoryJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<CategoriaDto> MAPPER = new RowMapper<>() {
        @Override
        public CategoriaDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new CategoriaDto(
                rs.getInt("pk_idcategoria"),
                rs.getString("nombre_categoria")
            );
        }
    };

    // ---- Escrituras (procedimientos) ----
    @Override
    public void crear(String nombre) {
        final String sql = "CALL sp_crear_categoria(?)";
        try {
            jdbc.update(sql, nombre);
        } catch (DataAccessException dae) {
            throw wrap("Error al crear categoría", dae);
        }
    }

    @Override
    public void actualizar(Integer id, String nuevoNombre) {
        final String sql = "CALL sp_actualizar_categoria(?, ?)";
        try {
            jdbc.update(sql, id, nuevoNombre);
        } catch (DataAccessException dae) {
            throw wrap("Error al actualizar categoría", dae);
        }
    }

    @Override
    public void eliminar(Integer id) {
        final String sql = "CALL sp_eliminar_categoria(?)";
        try {
            jdbc.update(sql, id);
        } catch (DataAccessException dae) {
            throw wrap("Error al eliminar categoría", dae);
        }
    }

    // ---- Lecturas (vista/funciones) ----
    @Override
    public List<CategoriaDto> listarTodos() {
        final String sql = "SELECT * FROM v_categorias";
        return jdbc.query(sql, MAPPER);
    }

    @Override
    public Optional<CategoriaDto> buscarPorId(Integer id) {
        final String sql = "SELECT * FROM fn_categoria_por_id(?)";
        List<CategoriaDto> list = jdbc.query(sql, MAPPER, id);
        return list.stream().findFirst();
    }

    @Override
    public List<CategoriaDto> buscarPorNombre(String filtro) {
        final String sql = "SELECT * FROM fn_categorias_por_nombre(?)";
        return jdbc.query(sql, MAPPER, filtro);
    }

    private RuntimeException wrap(String ctx, DataAccessException dae) {
        String msg = dae.getMostSpecificCause() != null
                ? dae.getMostSpecificCause().getMessage()
                : dae.getMessage();
        return new RuntimeException(ctx + ": " + msg, dae);
    }

}
