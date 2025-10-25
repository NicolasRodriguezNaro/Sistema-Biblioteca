package com.universidad.biblioteca.backend_server.requests;

import jakarta.validation.constraints.NotBlank;

public class CambiarEstadoRequest {
    @NotBlank
    private String estado; // 'activo' | 'inactivo' | 'suspendido'
    public String getEstado(){ return estado; }
    public void setEstado(String estado){ this.estado = estado; }
}
