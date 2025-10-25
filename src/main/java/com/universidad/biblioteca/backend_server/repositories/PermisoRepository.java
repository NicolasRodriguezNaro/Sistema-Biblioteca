package com.universidad.biblioteca.backend_server.repositories;

import java.util.List;

public interface PermisoRepository {
    // Devuelve SOLO el nombre del permiso (tu filtro hace "perm:" + p)
    List<String> permisosPorUsuario(Integer idUsuario);
}
