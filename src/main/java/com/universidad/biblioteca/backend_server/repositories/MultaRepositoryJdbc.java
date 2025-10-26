package com.universidad.biblioteca.backend_server.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.universidad.biblioteca.backend_server.dto.MultaDto;

@Repository
public class MultaRepositoryJdbc implements MultaRepository{

    private final JdbcTemplate jdbc;
    public MultaRepositoryJdbc(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private static final RowMapper<MultaDto> MAPPER = new RowMapper<>() {
        @Override
        public MultaDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new MultaDto(
                rs.getInt("num_multa"),
                rs.getInt("id_prestamo"),
                rs.getInt("id_tipo_multa"),
                rs.getString("nombre_tipo"),
                rs.getBigDecimal("monto_tipo"),
                rs.getObject("fecha_multa", LocalDate.class),
                rs.getString("estado_multa"),
                rs.getInt("id_usuario"),
                rs.getString("correo_usuario"),
                rs.getInt("id_libro"),
                rs.getString("titulo_libro"),
                rs.getString("isbn_libro")
            );
        }
    };

    @Override
    public void crear(Integer idPrestamo, Integer idTipoMulta, LocalDate fechaNullable, String estadoNullable) {
        // Hacemos CAST del estado al enum; si viene null que el SP use 'pendiente'
        final String call = "CALL sp_crear_multa(?, ?, ?, COALESCE(?::estado_multa, 'pendiente'))";
        try {
            jdbc.update(call, idPrestamo, idTipoMulta, fechaNullable, estadoNullable);
        } catch (DataAccessException dae) {
            throw wrap("Error al crear multa", dae);
        }
    }

    @Override
    public void pagar(Integer idPrestamo, Integer numMulta) {
        final String call = "CALL sp_pagar_multa(?, ?)";
        try {
            jdbc.update(call, idPrestamo, numMulta);
        } catch (DataAccessException dae) {
            throw wrap("Error al pagar multa", dae);
        }
    }

    @Override
    public void anular(Integer idPrestamo, Integer numMulta) {
        final String call = "CALL sp_anular_multa(?, ?)";
        try {
            jdbc.update(call, idPrestamo, numMulta);
        } catch (DataAccessException dae) {
            throw wrap("Error al anular multa", dae);
        }
    }

    // ---- Lecturas ----
    @Override public List<MultaDto> listar() {
        return jdbc.query("SELECT * FROM fn_multas()", MAPPER);
    }
    @Override public List<MultaDto> listarPorPrestamo(Integer idPrestamo) {
        return jdbc.query("SELECT * FROM fn_multas_por_prestamo(?)", MAPPER, idPrestamo);
    }
    @Override public List<MultaDto> listarPorUsuario(Integer idUsuario) {
        return jdbc.query("SELECT * FROM fn_multas_por_usuario(?)", MAPPER, idUsuario);
    }
    @Override public List<MultaDto> listarPorLibro(Integer idLibro) {
        return jdbc.query("SELECT * FROM fn_multas_por_libro(?)", MAPPER, idLibro);
    }
    @Override public List<MultaDto> listarPendientesPorUsuario(Integer idUsuario) {
        return jdbc.query("SELECT * FROM fn_multas_pendientes_usuario(?)", MAPPER, idUsuario);
    }

    private RuntimeException wrap(String ctx, DataAccessException dae) {
        String msg = dae.getMostSpecificCause() != null
                ? dae.getMostSpecificCause().getMessage()
                : dae.getMessage();
        return new RuntimeException(ctx + ": " + msg, dae);
    }
}
