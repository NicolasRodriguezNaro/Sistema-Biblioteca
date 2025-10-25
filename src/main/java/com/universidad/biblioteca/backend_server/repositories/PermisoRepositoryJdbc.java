package com.universidad.biblioteca.backend_server.repositories;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PermisoRepositoryJdbc implements PermisoRepository{
    
    private final JdbcTemplate jdbc;

    public PermisoRepositoryJdbc(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    @Override
    public List<String> permisosPorUsuario(Integer idUsuario) {
        // Leemos de la FUNCIÓN de permisos efectivos por usuario
        final String sql = "SELECT nombre_permiso FROM fn_permisos_por_usuario(?)";
        try {
            return jdbc.query(sql, (rs, i) -> rs.getString("nombre_permiso"), idUsuario);
        } catch (DataAccessException dae) {
            String msg = dae.getMostSpecificCause()!=null ? dae.getMostSpecificCause().getMessage() : dae.getMessage();
            throw new RuntimeException("Error al obtener permisos: " + msg, dae);
        }
    }
}
