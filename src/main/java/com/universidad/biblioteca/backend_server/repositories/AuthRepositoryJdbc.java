package com.universidad.biblioteca.backend_server.repositories;

import java.time.Instant;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuthRepositoryJdbc implements AuthRepository{
    
    private final JdbcTemplate jdbc;
    public AuthRepositoryJdbc(JdbcTemplate jdbc){ this.jdbc = jdbc; }

    @Override
    public Optional<UserAuth> findByCorreo(String correoLower) {
        final String sql = """
            SELECT id_usuario, correo, contrasena, estado, correo_verificado, token_version
            FROM fn_usuario_auth_por_correo(?)
            """;
        try {
            return jdbc.query(sql, rs -> {
                if (!rs.next()) return Optional.empty();
                return Optional.of(new UserAuth(
                    rs.getInt("id_usuario"),
                    rs.getString("correo"),
                    rs.getString("contrasena"),
                    rs.getString("estado"),
                    rs.getBoolean("correo_verificado"),
                    rs.getInt("token_version")
                ));
            }, correoLower);
        } catch (DataAccessException dae) {
            throw wrap("Error al buscar usuario por correo", dae);
        }
    }

    @Override
    public void registrarUsuario(Integer idPersona, String correo, String hash, Integer idRol) {
        final String call = "CALL sp_registrar_usuario(?, ?, ?, ?)";
        try {
            jdbc.update(call, idPersona, correo, hash, idRol);
        } catch (DataAccessException dae) {
            throw wrap("Error al registrar usuario", dae);
        }
    }

    @Override
    public void guardarTokenVerificacion(Integer idUsuario, String token, Instant expiresAt) {
        final String call = "CALL sp_guardar_token_verificacion(?, ?, ?)";
        try {
            jdbc.update(call, idUsuario, token, java.sql.Timestamp.from(expiresAt));
        } catch (DataAccessException dae) {
            throw wrap("Error al guardar token de verificación", dae);
        }
    }

    @Override
    public void verificarCorreoPorToken(String token) {
        final String call = "CALL sp_verificar_correo_por_token(?)";
        try {
            jdbc.update(call, token);
        } catch (DataAccessException dae) {
            throw wrap("Error al verificar correo", dae);
        }
    }

    @Override
    public Optional<UserAuth> findById(Integer idUsuario) {
        final String sql = """
            SELECT id_usuario, correo, contrasena, estado, correo_verificado, token_version
            FROM fn_usuario_auth_por_id(?)
            """;
        try {
            return jdbc.query(sql, rs -> {
                if (!rs.next()) return Optional.empty();
                return Optional.of(new UserAuth(
                    rs.getInt("id_usuario"),
                    rs.getString("correo"),
                    rs.getString("contrasena"),
                    rs.getString("estado"),
                    rs.getBoolean("correo_verificado"),
                    rs.getInt("token_version")
                ));
            }, idUsuario);
        } catch (DataAccessException dae) {
            throw wrap("Error al buscar usuario por id", dae);
        }
    }

    private RuntimeException wrap(String ctx, DataAccessException dae) {
        String msg = dae.getMostSpecificCause()!=null ? dae.getMostSpecificCause().getMessage() : dae.getMessage();
        return new RuntimeException(ctx + ": " + msg, dae);
    }
}
