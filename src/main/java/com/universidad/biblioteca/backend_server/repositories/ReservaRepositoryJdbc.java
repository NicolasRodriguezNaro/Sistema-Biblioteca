package com.universidad.biblioteca.backend_server.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.universidad.biblioteca.backend_server.dto.ReservaDto;

@Repository
public class ReservaRepositoryJdbc implements ReservaRepository{
    private final JdbcTemplate jdbc;
    public ReservaRepositoryJdbc(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private static final RowMapper<ReservaDto> MAPPER = new RowMapper<>() {
        @Override
        public ReservaDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            LocalDate fecha = rs.getObject("fecha_reserva", LocalDate.class);
            return new ReservaDto(
                rs.getInt("pk_idreserva"),
                rs.getInt("fk_idusuario"),
                rs.getInt("fk_idlibro"),
                fecha,
                rs.getString("estado_reserva"),
                rs.getString("titulo_libro"),
                rs.getString("isbn_libro"),
                rs.getString("correo_usuario")
            );
        }
    };

    @Override
    public void crear(Integer idUsuario, Integer idLibro, LocalDate fechaReserva) {
        final String call = "CALL sp_crear_reserva(?, ?, ?)";
        try {
            jdbc.update(call, idUsuario, idLibro, fechaReserva);
        } catch (DataAccessException dae) {
            throw wrap("Error al crear reserva", dae);
        }
    }

    @Override
    public void cancelar(Integer idReserva) {
        final String call = "CALL sp_cancelar_reserva(?)";
        try {
            jdbc.update(call, idReserva);
        } catch (DataAccessException dae) {
            throw wrap("Error al cancelar reserva", dae);
        }
    }

    @Override
    public List<ReservaDto> listar() {
        return jdbc.query("SELECT * FROM fn_reservas()", MAPPER);
    }

    @Override
    public Optional<ReservaDto> buscarPorId(Integer idReserva) {
        List<ReservaDto> list = jdbc.query("SELECT * FROM fn_reserva_por_id(?)", MAPPER, idReserva);
        return list.stream().findFirst();
    }

    @Override
    public List<ReservaDto> listarPorUsuario(Integer idUsuario) {
        return jdbc.query("SELECT * FROM fn_reservas_por_usuario(?)", MAPPER, idUsuario);
    }

    @Override
    public List<ReservaDto> listarPorLibro(Integer idLibro) {
        return jdbc.query("SELECT * FROM fn_reservas_por_libro(?)", MAPPER, idLibro);
    }

    @Override
    public List<ReservaDto> listarActivasPorUsuario(Integer idUsuario) {
        return jdbc.query("SELECT * FROM fn_reservas_activas_usuario(?)", MAPPER, idUsuario);
    }

    @Override
    public List<ReservaDto> listarActivas() {
        return jdbc.query("SELECT * FROM fn_reservas_activas()", MAPPER);
    }

    @Override
    public List<ReservaDto> listarActivasPorLibro(Integer idLibro) {
        return jdbc.query("SELECT * FROM fn_reservas_activas_por_libro(?)", MAPPER, idLibro);
    }

    @Override
    public int contarActivasPorLibro(Integer idLibro) {
        return jdbc.queryForObject("SELECT fn_reservas_activas_count_por_libro(?)",
               Integer.class, idLibro);
    }

    private RuntimeException wrap(String ctx, DataAccessException dae) {
        String msg = dae.getMostSpecificCause() != null
                ? dae.getMostSpecificCause().getMessage()
                : dae.getMessage();
        return new RuntimeException(ctx + ": " + msg, dae);
    }
}
