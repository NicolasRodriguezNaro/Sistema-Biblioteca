package com.universidad.biblioteca.backend_server.requests;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public class ActualizarEstadoPrestamoRequest {
    @NotBlank
    private String estado; // 'activo','devuelto','retrasado','perdido','deterioro'
    private LocalDate fechaDevolucionReal; // requerido si estado='devuelto'

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDate getFechaDevolucionReal() { return fechaDevolucionReal; }
    public void setFechaDevolucionReal(LocalDate fechaDevolucionReal) { this.fechaDevolucionReal = fechaDevolucionReal; }
}
