package com.universidad.biblioteca.backend_server.dto;

import java.time.LocalDate;

public class MultaDto {
    
    private Integer num;               // num (PK parcial)
    private Integer idPrestamo;        // pfk_idprestamo (PK parcial)
    private Integer idTipoMulta;
    private String nombreTipo;
    private java.math.BigDecimal montoTipo;
    private LocalDate fechaMulta;
    private String estado;             // estado_multa
    private Integer idUsuario;
    private String correoUsuario;
    private Integer idLibro;
    private String tituloLibro;
    private String isbnLibro;

    public MultaDto() {}

    public MultaDto(Integer num, Integer idPrestamo, Integer idTipoMulta, String nombreTipo,
                    java.math.BigDecimal montoTipo, LocalDate fechaMulta, String estado,
                    Integer idUsuario, String correoUsuario, Integer idLibro,
                    String tituloLibro, String isbnLibro) {
        this.num = num; this.idPrestamo = idPrestamo; this.idTipoMulta = idTipoMulta;
        this.nombreTipo = nombreTipo; this.montoTipo = montoTipo; this.fechaMulta = fechaMulta;
        this.estado = estado; this.idUsuario = idUsuario; this.correoUsuario = correoUsuario;
        this.idLibro = idLibro; this.tituloLibro = tituloLibro; this.isbnLibro = isbnLibro;
    }

    // getters/setters
    public Integer getNum() { return num; }
    public void setNum(Integer num) { this.num = num; }
    public Integer getIdPrestamo() { return idPrestamo; }
    public void setIdPrestamo(Integer idPrestamo) { this.idPrestamo = idPrestamo; }
    public Integer getIdTipoMulta() { return idTipoMulta; }
    public void setIdTipoMulta(Integer idTipoMulta) { this.idTipoMulta = idTipoMulta; }
    public String getNombreTipo() { return nombreTipo; }
    public void setNombreTipo(String nombreTipo) { this.nombreTipo = nombreTipo; }
    public java.math.BigDecimal getMontoTipo() { return montoTipo; }
    public void setMontoTipo(java.math.BigDecimal montoTipo) { this.montoTipo = montoTipo; }
    public LocalDate getFechaMulta() { return fechaMulta; }
    public void setFechaMulta(LocalDate fechaMulta) { this.fechaMulta = fechaMulta; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public String getCorreoUsuario() { return correoUsuario; }
    public void setCorreoUsuario(String correoUsuario) { this.correoUsuario = correoUsuario; }
    public Integer getIdLibro() { return idLibro; }
    public void setIdLibro(Integer idLibro) { this.idLibro = idLibro; }
    public String getTituloLibro() { return tituloLibro; }
    public void setTituloLibro(String tituloLibro) { this.tituloLibro = tituloLibro; }
    public String getIsbnLibro() { return isbnLibro; }
    public void setIsbnLibro(String isbnLibro) { this.isbnLibro = isbnLibro; }
}
