package com.universidad.biblioteca.backend_server.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.universidad.biblioteca.backend_server.repositories.UsuarioAdminRepository;

@Service
public class UsuarioAdminServiceImpl implements UsuarioAdminService{
    private final UsuarioAdminRepository repo;
    private final PasswordEncoder encoder;

    public UsuarioAdminServiceImpl(UsuarioAdminRepository repo, PasswordEncoder encoder) {
        this.repo = repo; this.encoder = encoder;
    }

    @Override
    public void cambiarEstado(Integer idUsuario, String nuevoEstado) {
        repo.cambiarEstado(idUsuario, nuevoEstado);
    }

    @Override
    public void actualizarContrasena(Integer idUsuario, String nuevaContrasenaPlano) {
        String hash = encoder.encode(nuevaContrasenaPlano);
        repo.actualizarContrasena(idUsuario, hash);
    }

    @Override
    public void actualizarCorreo(Integer idUsuario, String nuevoCorreo) {
        repo.actualizarCorreo(idUsuario, nuevoCorreo == null ? null : nuevoCorreo.trim().toLowerCase());
    }
}
