package com.universidad.biblioteca.backend_server.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.universidad.biblioteca.backend_server.dto.LibroDto;

@Repository
public class LibroRepositoryJdbc implements LibroRepository{

    private final JdbcTemplate jdbc;

    public LibroRepositoryJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // --- RowMapper contra la vista v_libros y funciones que devuelven ese shape ---
    private static final RowMapper<LibroDto> MAPPER = new RowMapper<>() {
        @Override
        public LibroDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new LibroDto(
                rs.getInt("pk_idlibro"),
                rs.getString("isbn"),
                rs.getString("titulo"),
                rs.getString("descripcion"),
                rs.getInt("cantidad_ejemplares"),
                rs.getString("editorial"),
                rs.getInt("anio_publicacion"),
                rs.getBoolean("prestable"),
                rs.getInt("fk_idcategoria"),
                rs.getString("nombre_categoria")
            );
        }
    };

    // --- Escrituras: procedimientos ---
    @Override
    public void crear(LibroDto dto) {
        final String sql = "CALL sp_registrar_libro(?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbc.update(sql,
                dto.getIsbn(),
                dto.getTitulo(),
                dto.getDescripcion(),
                dto.getEditorial(),
                dto.getAnioPublicacion(),
                dto.getPrestable(),
                dto.getIdCategoria()
            );
        } catch (DataAccessException dae) {
            throw wrapPlpgsqlException("Error al registrar libro", dae);
        }
    }

    @Override
    public void actualizar(Integer idLibro, LibroDto dto) {
        final String sql = "CALL sp_actualizar_libro(?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbc.update(sql,
                idLibro,
                dto.getIsbn(),
                dto.getTitulo(),
                dto.getDescripcion(),
                dto.getEditorial(),
                dto.getAnioPublicacion(),
                dto.getPrestable(),
                dto.getIdCategoria()
            );
        } catch (DataAccessException dae) {
            throw wrapPlpgsqlException("Error al actualizar libro", dae);
        }
    }

    @Override
    public void cambiarPrestable(Integer idLibro, boolean prestable) {
        final String sql = "CALL sp_cambiar_prestabilidad(?, ?)";
        try {
            jdbc.update(sql, idLibro, prestable);
        } catch (DataAccessException dae) {
            throw wrapPlpgsqlException("Error al cambiar prestabilidad del libro", dae);
        }
    }

    // --- Lecturas: vista y funciones ---
    @Override
    public List<LibroDto> listarTodos() {
        final String sql = "SELECT * FROM v_libros ORDER BY pk_idlibro";
        return jdbc.query(sql, MAPPER);
    }

    @Override
    public Optional<LibroDto> buscarPorId(Integer idLibro) {
        final String sql = "SELECT * FROM fn_libro_por_id(?)";
        List<LibroDto> list = jdbc.query(sql, MAPPER, idLibro);
        return list.stream().findFirst();
    }

    @Override
    public List<LibroDto> buscarPorTitulo(String filtro) {
        final String sql = "SELECT * FROM fn_libros_por_titulo(?) ORDER BY pk_idlibro";
        return jdbc.query(sql, MAPPER, filtro);
    }

    // --- Helper: convertir DataAccessException (procedimiento) en RuntimeException legible ---
    private RuntimeException wrapPlpgsqlException(String contexto, DataAccessException dae) {
        // Mensaje raíz del driver (incluye texto de RAISE EXCEPTION)
        String mensaje = dae.getMostSpecificCause() != null
                ? dae.getMostSpecificCause().getMessage()
                : dae.getMessage();
        return new RuntimeException(contexto + ": " + mensaje, dae);
    }

}
