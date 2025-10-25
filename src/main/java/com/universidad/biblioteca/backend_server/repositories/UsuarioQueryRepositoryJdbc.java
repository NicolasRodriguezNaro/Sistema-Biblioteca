package com.universidad.biblioteca.backend_server.repositories;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioQueryRepositoryJdbc implements UsuarioQueryRepository{
    private final JdbcTemplate jdbc;
    public UsuarioQueryRepositoryJdbc(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    @Override
    public List<UsuarioListado> listarTodos() {
        final String sql = "SELECT * FROM fn_usuarios_todos()";
        try {
            return jdbc.query(sql, (rs, i) -> new UsuarioListado(
                rs.getInt("id_usuario"),
                rs.getString("correo"),
                rs.getString("estado_usuario"),
                rs.getInt("id_rol"),
                rs.getString("nombre_rol"),
                rs.getBoolean("correo_verificado"),
                rs.getInt("token_version"),
                rs.getInt("num_documento"),
                rs.getString("nombre_uno"),
                rs.getString("nombre_dos"),
                rs.getString("apellido_uno"),
                rs.getString("apellido_dos")
            ));
        } catch (DataAccessException dae) {
            throw wrap("Error al listar usuarios", dae);
        }
    }

    @Override
    public List<UsuarioListado> listarPorEstado(String estado) {
        final String sql = "SELECT * FROM fn_usuarios_por_estado(?::estado_usuario)";
        try {
            return jdbc.query(sql, (rs, i) -> new UsuarioListado(
                rs.getInt("id_usuario"),
                rs.getString("correo"),
                rs.getString("estado_usuario"),
                rs.getInt("id_rol"),
                rs.getString("nombre_rol"),
                rs.getBoolean("correo_verificado"),
                rs.getInt("token_version"),
                rs.getInt("num_documento"),
                rs.getString("nombre_uno"),
                rs.getString("nombre_dos"),
                rs.getString("apellido_uno"),
                rs.getString("apellido_dos")
            ), estado);
        } catch (DataAccessException dae) {
            throw wrap("Error al listar usuarios por estado", dae);
        }
    }

    private RuntimeException wrap(String ctx, DataAccessException dae) {
        String msg = dae.getMostSpecificCause() != null ? dae.getMostSpecificCause().getMessage() : dae.getMessage();
        return new RuntimeException(ctx + ": " + msg, dae);
    }
}
