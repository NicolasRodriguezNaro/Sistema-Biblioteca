package com.universidad.biblioteca.backend_server.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.universidad.biblioteca.backend_server.dto.TipoMultaDto;

@Repository
public class TipoMultaRepositoryJdbc implements TipoMultaRepository{

    private final JdbcTemplate jdbc;
    public TipoMultaRepositoryJdbc(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private static final RowMapper<TipoMultaDto> MAPPER = new RowMapper<>() {
        @Override
        public TipoMultaDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new TipoMultaDto(
                rs.getInt("pk_idtipomulta"),
                rs.getString("nombre_tipo"),
                rs.getBigDecimal("monto")
            );
        }
    };

    @Override
    public void crear(String nombre, java.math.BigDecimal monto) {
        final String call = "CALL sp_crear_tipo_multa(?, ?)";
        try {
            jdbc.update(call, nombre, monto);
        } catch (DataAccessException dae) {
            throw wrap("Error al crear tipo de multa", dae);
        }
    }

    @Override
    public List<TipoMultaDto> listar() {
        return jdbc.query("SELECT * FROM fn_tipos_multa()", MAPPER);
    }

    private RuntimeException wrap(String ctx, DataAccessException dae) {
        String msg = dae.getMostSpecificCause() != null
                ? dae.getMostSpecificCause().getMessage()
                : dae.getMessage();
        return new RuntimeException(ctx + ": " + msg, dae);
    }
}
