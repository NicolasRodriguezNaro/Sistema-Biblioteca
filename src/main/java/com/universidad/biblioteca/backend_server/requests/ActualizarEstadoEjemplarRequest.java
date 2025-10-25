package com.universidad.biblioteca.backend_server.requests;

import jakarta.validation.constraints.NotBlank;

public class ActualizarEstadoEjemplarRequest {
    @NotBlank
    private String estado; // 'disponible','prestado','reservado','deteriorado','perdido','solo_sala'

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
