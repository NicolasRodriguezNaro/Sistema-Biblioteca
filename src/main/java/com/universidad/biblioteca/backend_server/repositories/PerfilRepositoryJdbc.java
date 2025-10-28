package com.universidad.biblioteca.backend_server.repositories;

import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PerfilRepositoryJdbc implements PerfilRepository{
    
    private final JdbcTemplate jdbc;

    public PerfilRepositoryJdbc(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    @Override
    public Optional<Perfil> findPerfilById(Integer idUsuario) {
        // Leemos de la VISTA que no expone contraseña
        final String sql = """
            SELECT id_usuario,
                   correo,
                   estado_usuario::text AS estado_usuario,
                   id_rol,
                   nombre_rol,
                   correo_verificado,
                   token_version
            FROM v_perfil_usuario
            WHERE id_usuario = ?
            """;
        try {
            return jdbc.query(sql, rs -> {
                if (!rs.next()) return Optional.empty();
                return Optional.of(new Perfil(
                    rs.getInt("id_usuario"),
                    rs.getString("correo"),
                    rs.getString("estado_usuario"),
                    rs.getInt("id_rol"),
                    rs.getString("nombre_rol"),
                    rs.getBoolean("correo_verificado"),
                    rs.getInt("token_version")
                ));
            }, idUsuario);
        } catch (DataAccessException dae) {
            // Burbujea con contexto legible (tu GlobalExceptionHandler lo formatea)
            String msg = dae.getMostSpecificCause()!=null ? dae.getMostSpecificCause().getMessage() : dae.getMessage();
            throw new RuntimeException("Error al obtener perfil: " + msg, dae);
        }
    }
}
