package com.universidad.biblioteca.backend_server.repositories;

import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.universidad.biblioteca.backend_server.dto.PerfilDto;

@Repository
public class PerfilDetalleRepositoryJdbc implements PerfilDetalleRepository{
    
    private final JdbcTemplate jdbc;
    public PerfilDetalleRepositoryJdbc(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    @Override
    public Optional<PerfilDto> findDetalleById(Integer idUsuario) {
        final String sql = "SELECT * FROM fn_perfil_usuario_detalle_por_id(?)";
        try {
            return jdbc.query(sql, rs -> {
                if (!rs.next()) return Optional.empty();
                return Optional.of(map(rs));
            }, idUsuario);
        } catch (DataAccessException dae) {
            throw wrap("Error al obtener perfil detallado por id", dae);
        }
    }

    @Override
    public Optional<PerfilDto> findDetalleByCorreo(String correo) {
        final String sql = "SELECT * FROM fn_perfil_usuario_detalle_por_correo(?)";
        try {
            return jdbc.query(sql, rs -> {
                if (!rs.next()) return Optional.empty();
                return Optional.of(map(rs));
            }, correo);
        } catch (DataAccessException dae) {
            throw wrap("Error al obtener perfil detallado por correo", dae);
        }
    }

    private PerfilDto map(java.sql.ResultSet rs) throws java.sql.SQLException {
        return new PerfilDto(
            rs.getInt("id_usuario"),
            rs.getString("correo"),
            rs.getString("estado_usuario"),
            rs.getInt("id_rol"),
            rs.getString("nombre_rol"),
            rs.getInt("num_documento"),
            rs.getString("nombre_uno"),
            rs.getString("nombre_dos"),
            rs.getString("apellido_uno"),
            rs.getString("apellido_dos"),
            rs.getString("telefono"),
            rs.getString("direccion"),
            rs.getString("tipo_documento"),
            rs.getBoolean("correo_verificado"),
            rs.getInt("token_version")
        );
    }

    private RuntimeException wrap(String ctx, DataAccessException dae) {
        String msg = dae.getMostSpecificCause()!=null ? dae.getMostSpecificCause().getMessage() : dae.getMessage();
        return new RuntimeException(ctx + ": " + msg, dae);
    }
}
