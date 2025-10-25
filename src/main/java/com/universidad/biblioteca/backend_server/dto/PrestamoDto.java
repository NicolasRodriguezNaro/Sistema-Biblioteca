package com.universidad.biblioteca.backend_server.dto;

import java.time.LocalDate;

public class PrestamoDto {
    private Integer id;                    // pk_idprestamo
    private Integer idUsuario;             // fk_idusuario
    private Integer idLibro;               // fk_idlibro
    private Integer numeroEjemplar;        // fk_idnumero
    private Integer idReserva;             // fk_idreserva (nullable)
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionProgramada;
    private LocalDate fechaDevolucionReal; // nullable
    private String estado;                 // estado_prestamo
    private String tituloLibro;            // lectura
    private String isbnLibro;              // lectura
    private String estadoEjemplarActual;   // lectura
    private String correoUsuario;          // lectura

    public PrestamoDto() {}

    public PrestamoDto(Integer id, Integer idUsuario, Integer idLibro, Integer numeroEjemplar,
                       Integer idReserva, LocalDate fechaPrestamo, LocalDate fechaDevolucionProgramada,
                       LocalDate fechaDevolucionReal, String estado, String tituloLibro, String isbnLibro,
                       String estadoEjemplarActual, String correoUsuario) {
        this.id = id; this.idUsuario = idUsuario; this.idLibro = idLibro; this.numeroEjemplar = numeroEjemplar;
        this.idReserva = idReserva; this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionProgramada = fechaDevolucionProgramada;
        this.fechaDevolucionReal = fechaDevolucionReal; this.estado = estado;
        this.tituloLibro = tituloLibro; this.isbnLibro = isbnLibro;
        this.estadoEjemplarActual = estadoEjemplarActual; this.correoUsuario = correoUsuario;
    }

    // getters/setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public Integer getIdLibro() { return idLibro; }
    public void setIdLibro(Integer idLibro) { this.idLibro = idLibro; }
    public Integer getNumeroEjemplar() { return numeroEjemplar; }
    public void setNumeroEjemplar(Integer numeroEjemplar) { this.numeroEjemplar = numeroEjemplar; }
    public Integer getIdReserva() { return idReserva; }
    public void setIdReserva(Integer idReserva) { this.idReserva = idReserva; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }
    public LocalDate getFechaDevolucionProgramada() { return fechaDevolucionProgramada; }
    public void setFechaDevolucionProgramada(LocalDate fechaDevolucionProgramada) { this.fechaDevolucionProgramada = fechaDevolucionProgramada; }
    public LocalDate getFechaDevolucionReal() { return fechaDevolucionReal; }
    public void setFechaDevolucionReal(LocalDate fechaDevolucionReal) { this.fechaDevolucionReal = fechaDevolucionReal; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getTituloLibro() { return tituloLibro; }
    public void setTituloLibro(String tituloLibro) { this.tituloLibro = tituloLibro; }
    public String getIsbnLibro() { return isbnLibro; }
    public void setIsbnLibro(String isbnLibro) { this.isbnLibro = isbnLibro; }
    public String getEstadoEjemplarActual() { return estadoEjemplarActual; }
    public void setEstadoEjemplarActual(String estadoEjemplarActual) { this.estadoEjemplarActual = estadoEjemplarActual; }
    public String getCorreoUsuario() { return correoUsuario; }
    public void setCorreoUsuario(String correoUsuario) { this.correoUsuario = correoUsuario; }
}
