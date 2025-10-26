package com.universidad.biblioteca.backend_server.requests;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CrearMultaRequest {
    @NotNull @Min(1)
    private Integer idPrestamo;

    @NotNull @Min(1)
    private Integer idTipoMulta;

    // opcional: si no viene -> current_date en el SP
    private LocalDate fecha;

    // opcional: 'pendiente' | 'pagada' | 'anulada'. Si no viene -> 'pendiente'
    private String estado;

    public Integer getIdPrestamo() { return idPrestamo; }
    public void setIdPrestamo(Integer idPrestamo) { this.idPrestamo = idPrestamo; }
    public Integer getIdTipoMulta() { return idTipoMulta; }
    public void setIdTipoMulta(Integer idTipoMulta) { this.idTipoMulta = idTipoMulta; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
