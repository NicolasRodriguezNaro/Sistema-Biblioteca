package com.universidad.biblioteca.backend_server.repositories;

import java.util.List;

public interface UsuarioQueryRepository {
    List<UsuarioListado> listarTodos();

    List<UsuarioListado> listarPorEstado(String estado); // "activo", "inactivo", "suspendido"

    default List<UsuarioListado> listarActivos()     { return listarPorEstado("activo"); }
    default List<UsuarioListado> listarInactivos()   { return listarPorEstado("inactivo"); }
    default List<UsuarioListado> listarSuspendidos() { return listarPorEstado("suspendido"); }

    record UsuarioListado(
        Integer idUsuario,
        String correo,
        String estadoUsuario,
        Integer idRol,
        String nombreRol,
        Boolean correoVerificado,
        Integer tokenVersion,
        Integer numDocumento,
        String nombreUno,
        String nombreDos,
        String apellidoUno,
        String apellidoDos
    ) {}
}
