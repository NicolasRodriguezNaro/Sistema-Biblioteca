package com.universidad.biblioteca.backend_server.dto;

import java.time.LocalDate;

public class ReservaDto {
    
    private Integer id;           // pk_idreserva
    private Integer idUsuario;    // fk_idusuario (pfk persona en usuario)
    private Integer idLibro;      // fk_idlibro
    private LocalDate fechaReserva;
    private String estado;        // estado_reserva
    private String tituloLibro;   // lectura
    private String isbnLibro;     // lectura
    private String correoUsuario; // lectura

    public ReservaDto() {}

    public ReservaDto(Integer id, Integer idUsuario, Integer idLibro,
                      LocalDate fechaReserva, String estado,
                      String tituloLibro, String isbnLibro, String correoUsuario) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idLibro = idLibro;
        this.fechaReserva = fechaReserva;
        this.estado = estado;
        this.tituloLibro = tituloLibro;
        this.isbnLibro = isbnLibro;
        this.correoUsuario = correoUsuario;
    }

    // getters/setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public Integer getIdLibro() { return idLibro; }
    public void setIdLibro(Integer idLibro) { this.idLibro = idLibro; }
    public LocalDate getFechaReserva() { return fechaReserva; }
    public void setFechaReserva(LocalDate fechaReserva) { this.fechaReserva = fechaReserva; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getTituloLibro() { return tituloLibro; }
    public void setTituloLibro(String tituloLibro) { this.tituloLibro = tituloLibro; }
    public String getIsbnLibro() { return isbnLibro; }
    public void setIsbnLibro(String isbnLibro) { this.isbnLibro = isbnLibro; }
    public String getCorreoUsuario() { return correoUsuario; }
    public void setCorreoUsuario(String correoUsuario) { this.correoUsuario = correoUsuario; }
}
