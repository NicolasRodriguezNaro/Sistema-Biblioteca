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

import com.universidad.biblioteca.backend_server.dto.PrestamoDto;

@Repository
public class PrestamoRepositoryJdbc implements PrestamoRepository{
    private final JdbcTemplate jdbc;
    public PrestamoRepositoryJdbc(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private static final RowMapper<PrestamoDto> MAPPER = new RowMapper<>() {
        @Override
        public PrestamoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new PrestamoDto(
                rs.getInt("pk_idprestamo"),
                rs.getInt("fk_idusuario"),
                rs.getInt("fk_idlibro"),
                rs.getInt("fk_idnumero"),
                (Integer) rs.getObject("fk_idreserva"),
                rs.getObject("fecha_prestamo", LocalDate.class),
                rs.getObject("fecha_devolucion_programada", LocalDate.class),
                rs.getObject("fecha_devolucion_real", LocalDate.class),
                rs.getString("estado_prestamo"),
                rs.getString("titulo_libro"),
                rs.getString("isbn_libro"),
                rs.getString("estado_ejemplar_actual"),
                rs.getString("correo_usuario")
            );
        }
    };

    // ---- Escrituras ----
    @Override
    public void crear(Integer idUsuario, Integer idLibro, Integer numeroEjemplar,
                      LocalDate fechaPrestamo, LocalDate fechaDevolucionProgramada, Integer idReservaNullable) {
        final String call = "CALL sp_crear_prestamo(?, ?, ?, ?, ?, ?)";
        try {
            jdbc.update(call, idUsuario, idLibro, numeroEjemplar,
                    fechaPrestamo, fechaDevolucionProgramada, idReservaNullable);
        } catch (DataAccessException dae) {
            throw wrap("Error al crear préstamo", dae);
        }
    }

    @Override
    public void actualizarEstado(Integer idPrestamo, String nuevoEstado, LocalDate fechaDevolucionRealNullable) {
        // enum esquema_operaciones.estado_prestamo -> castear el parámetro
        final String call =
            "CALL sp_actualizar_estado_prestamo(?, ?::estado_prestamo, ?)";
        try {
            jdbc.update(call, idPrestamo, nuevoEstado, fechaDevolucionRealNullable);
        } catch (DataAccessException dae) {
            throw wrap("Error al actualizar estado del préstamo", dae);
        }
    }

    // ---- Lecturas ----
    @Override public List<PrestamoDto> listar() {
        return jdbc.query("SELECT * FROM fn_prestamos()", MAPPER);
    }

    @Override public Optional<PrestamoDto> buscarPorId(Integer idPrestamo) {
        List<PrestamoDto> l = jdbc.query("SELECT * FROM fn_prestamo_por_id(?)", MAPPER, idPrestamo);
        return l.stream().findFirst();
    }

    @Override public List<PrestamoDto> listarPorUsuario(Integer idUsuario) {
        return jdbc.query("SELECT * FROM fn_prestamos_por_usuario(?)", MAPPER, idUsuario);
    }

    @Override public List<PrestamoDto> listarPorLibro(Integer idLibro) {
        return jdbc.query("SELECT * FROM fn_prestamos_por_libro(?)", MAPPER, idLibro);
    }

    @Override public List<PrestamoDto> listarActivosPorUsuario(Integer idUsuario) {
        return jdbc.query("SELECT * FROM fn_prestamos_activos_usuario(?)", MAPPER, idUsuario);
    }

    @Override public List<PrestamoDto> listarRetrasadosPorUsuario(Integer idUsuario) {
        return jdbc.query("SELECT * FROM fn_prestamos_retrasados_usuario(?)", MAPPER, idUsuario);
    }

    @Override
    public List<PrestamoDto> listarActivos() {
        return jdbc.query("SELECT * FROM fn_prestamos_activos()", MAPPER);
    }

    @Override
    public List<PrestamoDto> listarActivosPorLibro(Integer idLibro) {
        return jdbc.query("SELECT * FROM fn_prestamos_activos_por_libro(?)", MAPPER, idLibro);
    }

    @Override
    public int contarActivosPorLibro(Integer idLibro) {
        return jdbc.queryForObject("SELECT fn_prestamos_activos_count_por_libro(?)",
        Integer.class, idLibro);
    }

    private RuntimeException wrap(String ctx, DataAccessException dae) {
        String msg = dae.getMostSpecificCause() != null
                ? dae.getMostSpecificCause().getMessage()
                : dae.getMessage();
        return new RuntimeException(ctx + ": " + msg, dae);
    }
}
