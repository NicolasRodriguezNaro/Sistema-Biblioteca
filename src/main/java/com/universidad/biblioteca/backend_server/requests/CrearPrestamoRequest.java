package com.universidad.biblioteca.backend_server.requests;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public class CrearPrestamoRequest {
    @NotNull private Integer idLibro;        // pk_idlibro
    @NotNull private Integer numeroEjemplar; // numero del ejemplar
    @NotNull private LocalDate fechaPrestamo;
    @NotNull private LocalDate fechaDevolucionProgramada;
    private Integer idReserva;               // opcional

    public Integer getIdLibro() { return idLibro; }
    public void setIdLibro(Integer idLibro) { this.idLibro = idLibro; }
    public Integer getNumeroEjemplar() { return numeroEjemplar; }
    public void setNumeroEjemplar(Integer numeroEjemplar) { this.numeroEjemplar = numeroEjemplar; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }
    public LocalDate getFechaDevolucionProgramada() { return fechaDevolucionProgramada; }
    public void setFechaDevolucionProgramada(LocalDate fechaDevolucionProgramada) { this.fechaDevolucionProgramada = fechaDevolucionProgramada; }
    public Integer getIdReserva() { return idReserva; }
    public void setIdReserva(Integer idReserva) { this.idReserva = idReserva; }
}
