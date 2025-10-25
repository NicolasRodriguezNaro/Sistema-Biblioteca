package com.universidad.biblioteca.backend_server.repositories;

public interface UsuarioAdminRepository {
    void cambiarEstado(Integer idUsuario, String nuevoEstado);       // sp_cambiar_estado_usuario
    void actualizarContrasena(Integer idUsuario, String hashNueva);  // sp_actualizar_contrasena
    void actualizarCorreo(Integer idUsuario, String nuevoCorreo);
}
