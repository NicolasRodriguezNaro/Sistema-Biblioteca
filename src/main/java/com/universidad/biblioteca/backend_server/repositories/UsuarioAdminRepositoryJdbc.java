package com.universidad.biblioteca.backend_server.repositories;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioAdminRepositoryJdbc implements UsuarioAdminRepository{
    private final JdbcTemplate jdbc;
    public UsuarioAdminRepositoryJdbc(JdbcTemplate jdbc){ this.jdbc = jdbc; }

    @Override
    public void cambiarEstado(Integer idUsuario, String nuevoEstado) {
        // Enum: castear para evitar "no existe procedimiento (character varying)"
        final String call = "CALL sp_cambiar_estado_usuario(?, ?::estado_usuario)";
        try {
            jdbc.update(call, idUsuario, nuevoEstado);
        } catch (DataAccessException dae) {
            throw wrap("Error al cambiar estado de usuario", dae);
        }
    }

    @Override
    public void actualizarContrasena(Integer idUsuario, String hashNueva) {
        final String call = "CALL sp_actualizar_contrasena(?, ?)";
        try {
            jdbc.update(call, idUsuario, hashNueva);
        } catch (DataAccessException dae) {
            throw wrap("Error al actualizar contraseña de usuario", dae);
        }
    }

    @Override
    public void actualizarCorreo(Integer idUsuario, String nuevoCorreo) {
        final String call = "CALL sp_actualizar_correo(?, ?)";
        try {
            jdbc.update(call, idUsuario, nuevoCorreo);
        } catch (DataAccessException dae) {
            throw wrap("Error al actualizar correo de usuario", dae);
        }
    }

    private RuntimeException wrap(String ctx, DataAccessException dae){
        String msg = dae.getMostSpecificCause()!=null ? dae.getMostSpecificCause().getMessage() : dae.getMessage();
        return new RuntimeException(ctx + ": " + msg, dae);
    }
}
