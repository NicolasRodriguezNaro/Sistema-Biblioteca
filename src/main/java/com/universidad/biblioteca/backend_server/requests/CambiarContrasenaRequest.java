package com.universidad.biblioteca.backend_server.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CambiarContrasenaRequest {
    @NotBlank @Size(min=8, max=100)
    private String nuevaContrasena;
    public String getNuevaContrasena(){ return nuevaContrasena; }
    public void setNuevaContrasena(String v){ this.nuevaContrasena = v; }
}
