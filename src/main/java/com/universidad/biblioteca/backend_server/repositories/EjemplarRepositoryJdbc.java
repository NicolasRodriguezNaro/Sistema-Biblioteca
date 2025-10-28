package com.universidad.biblioteca.backend_server.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.universidad.biblioteca.backend_server.dto.EjemplarDto;

@Repository
public class EjemplarRepositoryJdbc implements EjemplarRepository{

    private final JdbcTemplate jdbc;

    public EjemplarRepositoryJdbc(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private static final RowMapper<EjemplarDto> MAPPER = new RowMapper<>() {
        @Override
        public EjemplarDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new EjemplarDto(
                rs.getInt("id_libro"),
                rs.getInt("numero"),
                rs.getString("estado"),
                rs.getString("titulo_libro"),
                rs.getBoolean("prestable_libro")
            );
        }
    };

    @Override
    public Integer agregarUno(Integer idLibro) {
        final String sql = "SELECT fn_agregar_ejemplar_y_retornar(?)";
        try {
            Integer numero = jdbc.queryForObject(sql, Integer.class, idLibro);
            if (numero == null) throw new RuntimeException("No se pudo obtener el número del ejemplar creado");
            return numero;
        } catch (DataAccessException dae) {
            throw wrap("Error al agregar ejemplar", dae);
        }
    }

    @Override
    public void eliminar(Integer idLibro, Integer numero) {
        final String call = "CALL sp_eliminar_ejemplar(?, ?)";
        try {
            jdbc.update(call, idLibro, numero);
        } catch (DataAccessException dae) {
            throw wrap("Error al eliminar ejemplar", dae);
        }
    }

    @Override
    public void actualizarEstado(Integer idLibro, Integer numero, String nuevoEstado) {
        final String call = "CALL sp_actualizar_estado_ejemplar(?, ?, ?::estado_ejemplar)";
        try {
            jdbc.update(call, idLibro, numero, nuevoEstado);
        } catch (DataAccessException dae) {
            throw wrap("Error al actualizar estado de ejemplar", dae);
        }
    }

    @Override
    public List<EjemplarDto> listarPorLibro(Integer idLibro) {
        final String sql = "SELECT * FROM fn_ejemplares_por_libro(?)";
        return jdbc.query(sql, MAPPER, idLibro);
    }

    @Override
    public List<EjemplarDto> listarDisponibles(Integer idLibro) {
        final String sql = "SELECT * FROM fn_ejemplares_disponibles(?)";
        return jdbc.query(sql, MAPPER, idLibro);
    }

    @Override
    public Optional<EjemplarDto> obtener(Integer idLibro, Integer numero) {
        final String sql = "SELECT * FROM fn_ejemplar_por_libro_numero(?, ?)";
        List<EjemplarDto> list = jdbc.query(sql, MAPPER, idLibro, numero);
        return list.stream().findFirst();
    }

    private RuntimeException wrap(String ctx, DataAccessException dae) {
        String msg = dae.getMostSpecificCause() != null
                ? dae.getMostSpecificCause().getMessage()
                : dae.getMessage();
        return new RuntimeException(ctx + ": " + msg, dae);
    }
}
