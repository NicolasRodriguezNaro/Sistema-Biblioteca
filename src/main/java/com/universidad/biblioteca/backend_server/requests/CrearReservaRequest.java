package com.universidad.biblioteca.backend_server.requests;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public class CrearReservaRequest {
    @NotNull private Integer idUsuario;   // pfk_idpersona (en usuario)
    @NotNull private Integer idLibro;     // pk_idlibro
    @NotNull private LocalDate fecha;     // ISO yyyy-MM-dd

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public Integer getIdLibro() { return idLibro; }
    public void setIdLibro(Integer idLibro) { this.idLibro = idLibro; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}
