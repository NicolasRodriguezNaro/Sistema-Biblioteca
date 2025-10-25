package com.universidad.biblioteca.backend_server.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CambiarCorreoRequest {
    @NotBlank @Email
    private String nuevoCorreo;

    public String getNuevoCorreo() { return nuevoCorreo; }
    public void setNuevoCorreo(String nuevoCorreo) { this.nuevoCorreo = nuevoCorreo; }
}
