package com.universidad.biblioteca.backend_server.services;

import org.springframework.stereotype.Service;

import com.universidad.biblioteca.backend_server.repositories.PerfilRepository;
import com.universidad.biblioteca.backend_server.repositories.PermisoRepository;
import com.universidad.biblioteca.backend_server.responses.PerfilResponse;

@Service
public class PerfilServiceImpl implements PerfilService{
    
    private final PerfilRepository perfilRepository;
    private final PermisoRepository permisoRepository;

    public PerfilServiceImpl(PerfilRepository perfilRepository, PermisoRepository permisoRepository) {
        this.perfilRepository = perfilRepository;
        this.permisoRepository = permisoRepository;
    }

    @Override
    public PerfilResponse obtenerPerfilCompleto(Integer idUsuario) {
        var perfil = perfilRepository.findPerfilById(idUsuario)
                .orElseThrow(() -> new RuntimeException("No existe usuario con id " + idUsuario));

        var permisos = permisoRepository.permisosPorUsuario(idUsuario);

        return new PerfilResponse(
                perfil.idUsuario(),
                perfil.correo(),
                perfil.estadoUsuario(),
                perfil.idRol(),
                perfil.nombreRol(),
                perfil.correoVerificado(),
                perfil.tokenVersion(),
                permisos
        );
    }
}
