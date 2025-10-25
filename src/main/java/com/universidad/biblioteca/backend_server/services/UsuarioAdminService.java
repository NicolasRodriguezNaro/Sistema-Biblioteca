package com.universidad.biblioteca.backend_server.services;

public interface UsuarioAdminService {
    void cambiarEstado(Integer idUsuario, String nuevoEstado);
    void actualizarContrasena(Integer idUsuario, String nuevaContrasenaPlano);
    void actualizarCorreo(Integer idUsuario, String nuevoCorreo);
}
